package io.github.StardewValley.views.menu.GUI;

import Asset.SharedAssetManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import data.ArtisanRecipeData;
import data.items.AllItemsData;
import io.github.StardewValley.Animations.ExclamationMarkAnimation;
import io.github.StardewValley.controllers.GameController;
import models.Item.Item;
import models.game.Player;
import models.map.Artisan;
import models.result.Result;
import models.result.errorTypes.GameError;
import org.apache.tools.ant.util.TeeOutputStream;

import java.util.ArrayList;
import java.util.Map;

public class ArtisanTab {
	private final Player player;
	private GameScreen gameScreen;
	private int cursorX, cursorY;
	private GameController gc;
	private Artisan artisan;

	private Skin skin;
	private Stage stage;
	private Label titleLabel;
	private ProgressBar progressBar;
	private boolean finishCheat = false;
	private boolean pressed = false;
	private TextButton finishButton;

	BitmapFont numberFont;

	public ArtisanTab(Player player, GameScreen gameScreen, Skin skin) {
		this.player = player;
		this.gameScreen = gameScreen;
		this.skin = skin;
		stage = new Stage(new ScreenViewport());

		setNumberFont();
	}

	private void createUI() {
		progressBar = null;
		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);
		Table mainTable = new Table();
		mainTable.setBackground(skin.newDrawable("white", Color.LIGHT_GRAY));

		titleLabel = new Label("", skin);
		titleLabel.setText(artisan.getName());
		titleLabel.setAlignment(Align.center);
		mainTable.add(titleLabel).colspan(3).padTop(10).expandX().fillX().center();
		mainTable.row();

		if (artisan.isEmpty()) {
			createEmptyUI(mainTable);
		}
		else if (artisan.isResultReady()) {
			createReadyUI(mainTable);
		}
		else {
			createProcessingUI(mainTable);
		}

		TextButton closeButton = new TextButton("X", skin);
		closeButton.setColor(Color.RED);
		closeButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				stage.dispose();
				gameScreen.onArtisanTabClosed();
			}
		});

		mainTable.add(closeButton).padTop(10).padRight(10).right().expandX();

		mainTable.pack();

		float width = mainTable.getWidth();
		float height = mainTable.getHeight();

		int x = cursorX;
		int y = cursorY;
		if (x + width > Gdx.graphics.getWidth() - 60) {
			System.out.println("Change X");
			x -= width;
		}
		if (y + height > Gdx.graphics.getHeight() - 60) {
			System.out.println("Change Y");
			y -= height;
		}
		mainTable.setPosition(x, Gdx.graphics.getHeight() - y, Align.topLeft);
		stage.addActor(mainTable);
	}

	private void createEmptyUI(Table mainTable) {
		Table contentTable = new Table();
		contentTable.pad(20);

		for (ArtisanRecipeData recipe: artisan.getRecipeData().getRecipes()) {
			Image resultImage = new Image(SharedAssetManager.getOrLoad(AllItemsData.getData(recipe.getName()).getTextureAddress()));
			resultImage.setSize(64, 64);
			contentTable.add(resultImage).padRight(10);
			contentTable.add(new Label(":", skin)).padRight(10);

			boolean firstCol = true;
			for (Map<String, Integer> ing: recipe.getIngredients()) {
				if (!firstCol) {
					contentTable.add().padRight(10);
					contentTable.add().padRight(10);
				}
				firstCol = false;
				for (String itemName: ing.keySet()) {
					Image img = new Image(SharedAssetManager.getOrLoad(AllItemsData.getData(itemName).getTextureAddress()));
					img.setSize(64, 64);
					if (player.getInventory().canRemoveItem(Item.build(itemName, ing.get(itemName))))
						contentTable.add(mergeActorAndNumber(img, ing.get(itemName), Color.WHITE)).padRight(10);
					else
						contentTable.add(mergeActorAndNumber(img, ing.get(itemName), Color.RED)).padRight(10);
				}
				TextButton startButton = new TextButton("Start", skin);
				startButton.addListener(new ClickListener() {
					@Override
					public void clicked(InputEvent event, float x, float y) {
						Result<Void> result = gc.useArtisan(artisan, recipe.getName(), ing);
						if (result.isSuccess()) {
							createUI();
							ShowMap.addExclamationMark(new ExclamationMarkAnimation().show(artisan, cursorX - 20, cursorY - 30));
						}
						else if (result.getError() == GameError.NOT_ENOUGH_ITEMS) {
							notEnoughItemDialog().show(stage);
						}
					}
				});
				contentTable.add(startButton).width(80).padLeft(10);
				contentTable.row().padTop(10);

			}
		}

		ScrollPane scrollPane = new ScrollPane(contentTable, skin);
		scrollPane.setScrollingDisabled(true, false);
		scrollPane.setFadeScrollBars(false);

		mainTable.add(scrollPane).height(Math.min(scrollPane.getHeight(), 500));
		mainTable.row();
	}

	private void createProcessingUI(Table mainTable) {
		Table contentTable = new Table();
		contentTable.pad(20);

		String resultName = artisan.getResultName();

		Label itemInProcessLabel = new Label("Item In Process: ", skin);
		Image itemInProcessImage = new Image(SharedAssetManager.getOrLoad(AllItemsData.getData(resultName).getTextureAddress()));;
		contentTable.add(itemInProcessLabel).padRight(10);
		contentTable.add(itemInProcessImage).row();

		Texture bgTexture = createColoredTexture(1, 30, Color.DARK_GRAY);
		Texture fillTexture = createColoredTexture(1, 30, Color.GREEN);

		ProgressBar.ProgressBarStyle progressBarStyle = new ProgressBar.ProgressBarStyle();
		progressBarStyle.background = new TextureRegionDrawable(new TextureRegion(bgTexture));
		progressBarStyle.knobBefore = new TextureRegionDrawable(new TextureRegion(fillTexture));

		progressBar = new ProgressBar(0, 1, 0.01f, false, progressBarStyle);
		progressBar.setValue(artisan.getPercentage());
		contentTable.add(progressBar).colspan(2).width(500).height(30).pad(20).row();

		TextButton cancelButton = new TextButton("Cancel", skin);
		cancelButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				gc.cancelArtisan(artisan);
				stage.dispose();
				createUI();
			}
		});

		if (finishCheat) {
			finishButton = new TextButton("Finish", skin);
			finishButton.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					artisan.finish();
				}
			});
			contentTable.add(finishButton);
		}
		else {
			contentTable.add();
		}
		contentTable.add(cancelButton);

		mainTable.add(contentTable);
		mainTable.row();
	}

	private void createReadyUI(Table mainTable) {
		Table contentTable = new Table();
		contentTable.pad(20);
		String resultName = artisan.getResultWithoutReset().getName();
		Image resultImage = new Image(SharedAssetManager.getOrLoad(AllItemsData.getData(resultName).getTextureAddress()));
		resultImage.setSize(64, 64);
		contentTable.add(resultImage).row();

		Label resultNameLabel = new Label(resultName + " is ready", skin);
		contentTable.add(resultNameLabel).padRight(10);

		TextButton receiveButton = new TextButton("Receive", skin);
		receiveButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				gc.getArtisanProduct(artisan);
				createUI();
				ShowMap.removeExclamationMark(artisan);
			}
		});
		contentTable.add(receiveButton).padRight(10);

		mainTable.add(contentTable);
		mainTable.row();
	}

	public void show(Artisan artisan, GameController gc, int cursorX, int cursorY) {
		this.artisan = artisan;
		this.gc = gc;
		this.cursorX = cursorX;
		this.cursorY = cursorY;
		finishCheat = false;
		pressed = false;

		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);
		createUI();
	}

	public void draw() {
		if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
			gameScreen.onArtisanTabClosed();
		}

		if (artisan.isProcessing()) {
			progressBar.setValue(artisan.getPercentage());

			if (Gdx.input.isKeyPressed(Input.Keys.C)) {
				if (!pressed) {
					pressed = true;
					finishCheat = !finishCheat;
					createUI();
				}
			}
			else {
				pressed = false;
			}
		}
		if (!artisan.isProcessing() && progressBar != null) {
			createUI();
		}
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}

	public void dispose() {
		stage.dispose();
	}

	private void setNumberFont() {
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/Blomberg-8MKKZ.otf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

		parameter.size = 24;
		parameter.color = Color.WHITE;
		parameter.borderWidth = 2;
		parameter.borderColor = Color.BLACK;
		parameter.borderStraight = true;

		numberFont = generator.generateFont(parameter);
		generator.dispose();
	}

	private Stack mergeActorAndNumber(Actor actor, int number, Color color) {
		Stack stack = new Stack();
		stack.add(actor);
		Label.LabelStyle labelStyle = new Label.LabelStyle(numberFont, color);
		Label numberLabel = new Label(String.valueOf(number), labelStyle);

		Container<Label> numberContainer = new Container<>(numberLabel);
		numberContainer.bottom().right();
		numberContainer.padBottom(5).padRight(5); // Add some padding
		stack.add(numberContainer);
		return stack;
	}

	private Dialog notEnoughItemDialog() {
		Dialog dialog = new Dialog("Insufficient resources", skin);
		dialog.button("Ok");
		return dialog;
	}

	private Texture createColoredTexture(int width, int height, Color color) {
		Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
		pixmap.setColor(color);
		pixmap.fill();
		Texture texture = new Texture(pixmap);
		pixmap.dispose();
		return texture;
	}
}