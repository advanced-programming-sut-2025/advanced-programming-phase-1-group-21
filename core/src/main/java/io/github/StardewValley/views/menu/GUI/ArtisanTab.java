package io.github.StardewValley.views.menu.GUI;

import Asset.SharedAssetManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import data.ArtisanRecipeData;
import data.items.AllItemsData;
import io.github.StardewValley.controllers.GameController;
import models.Item.Item;
import models.game.Player;
import models.map.Artisan;
import models.result.Result;
import models.result.errorTypes.GameError;

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

	BitmapFont numberFont;

	public ArtisanTab(Player player, GameScreen gameScreen, Skin skin) {
		this.player = player;
		this.gameScreen = gameScreen;
		this.skin = skin;
		stage = new Stage(new ScreenViewport());
		titleLabel = new Label("", skin);

		setNumberFont();
	}

	private void createUI() {
		Table mainTable = new Table();
		mainTable.setBackground(skin.newDrawable("white", Color.LIGHT_GRAY));

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

		if (cursorX + width > Gdx.graphics.getWidth() - 60) {
			System.out.println("Change X");
			cursorX -= width;
		}
		if (cursorY + height > Gdx.graphics.getHeight() - 60) {
			System.out.println("Change Y");
			cursorY -= height;
		}
		mainTable.setPosition(cursorX, Gdx.graphics.getHeight() - cursorY, Align.topLeft);
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

		mainTable.add(contentTable);
		mainTable.row();
	}

	private void createProcessingUI(Table mainTable) {
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
		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);
		createUI();
	}

	public void draw() {
		if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
			System.out.println("TEST");
			gameScreen.onArtisanTabClosed();
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
}