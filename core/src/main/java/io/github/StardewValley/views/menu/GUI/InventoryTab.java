package io.github.StardewValley.views.menu.GUI;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import data.items.AllItemsData;
import io.github.StardewValley.App;
import models.Item.Item;
import models.Item.ItemType;
import models.game.Player;
import models.skill.SkillType;
import models.tool.Tool;

import java.util.ArrayList;
import java.util.List;

class InventoryTab {
	private Stage stage;
	private Skin skin;
	private GameScreen gameScreen;
//	private Dialog tableDialog;
	private Texture emptyTexture;
	private TextureRegionDrawable emptyDrawable, whiteSquare, redSquare;
	private TextButton exitButton, inventoryButton, socialButton, mapButton, settingButton, skillButton, missionButton;
	private Label farmingSkillLabel, miningSkillLabel, foragingSkillLabel, fishingSkillLabel;
	private ArrayList<Image> farmingLevels, miningLevels, foragingLevels, fishingLevels;


	private ScrollPane inventoryScrollPane;
	BitmapFont numberFont;

	private int dialogWidth, dialogHeight;
	private Texture sampleTexture;

	public InventoryTab(GameScreen gameScreen, Skin skin) {
		this.gameScreen = gameScreen;
		this.skin = skin;
		farmingLevels = new ArrayList<>();
		miningLevels = new ArrayList<>();
		foragingLevels = new ArrayList<>();
		fishingLevels = new ArrayList<>();
		createUI();
	}

	void createUI() {
		Player player = App.getInstance().game.getCurrentPlayer();
		stage = new Stage(new ScreenViewport());
		setEmpty();
		setNumberFont();

		sampleTexture = new Texture(AllItemsData.getData("Wool").getTextureAddress());

		// Create Dialog
		dialogWidth = Gdx.graphics.getWidth() / 2;
		dialogHeight = Gdx.graphics.getHeight() / 2;
//		tableDialog = new Dialog("", skin) {
//			@Override
//			protected void result(Object object) {
//				gameScreen.onInventoryClosed();
//			}
//		};
//		tableDialog.setSize(dialogWidth, dialogHeight);
//		tableDialog.setPosition(
//				(Gdx.graphics.getWidth() - dialogWidth) / 2,
//				(Gdx.graphics.getHeight() - dialogHeight) / 2
//		);
//		tableDialog.setModal(true);
//		tableDialog.setMovable(false);

//		tableDialog.button("exit");
		// ایجاد جدول برای اسکرول
		Table scrollTable = new Table();

		scrollTable.top();
		scrollTable.defaults().width(64).height(64).pad(2);

		List<Item> items = player.getInventory().getItems();
		int rows = items.size() / 12;

		for (int row = 0; row < 70; row++) {
			for (int col = 0; col < 12; col++) {
				Image cell;
				int id = row * 12 + col;
				if (id < items.size()) {
					Item item = items.get(id);
					AllItemsData data = AllItemsData.getData(item.getName());
					if (data != null) {
						String address = data.getTextureAddress();
						if (address != null)
							cell = new Image(new TextureRegionDrawable(new TextureRegion(new Texture(address))));
						else {
							cell = new Image(new TextureRegionDrawable(new TextureRegion(sampleTexture)));
						}
					}
					else {
						if (item.getItemType() == ItemType.TOOL) {
							cell = new Image(new TextureRegionDrawable(new TextureRegion(new Texture("Textures/Tools/" + capitilize(((Tool) item)) + ".png"))));
						}
						else
							cell = new Image(new TextureRegionDrawable(new TextureRegion(sampleTexture)));
					}
				}
				else {
					cell = new Image(emptyDrawable);
				}
				scrollTable.add(cell);
			}
			scrollTable.row();
		}

		inventoryScrollPane = new ScrollPane(scrollTable, skin);
		inventoryScrollPane.setScrollingDisabled(true, false);
		inventoryScrollPane.setFlickScroll(true);
		inventoryScrollPane.setSmoothScrolling(true);
		inventoryScrollPane.setForceScroll(false, false);
		inventoryScrollPane.setOverscroll(false, false);
		inventoryScrollPane.setFadeScrollBars(false);
		inventoryScrollPane.setSize(850, 200);
		inventoryScrollPane.setPosition(610, 575);
		inventoryScrollPane.addListener(new InputListener() {
			@Override
			public boolean scrolled(InputEvent event, float x, float y, float amountX, float amountY) {
				System.out.println("Scroll detected: " + amountY);
				return super.scrolled(event, x, y, amountX, amountY);
			}
		});

		exitButton = new TextButton("X", skin);
		exitButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
//				tableDialog.cancel();
				gameScreen.onInventoryClosed();
			}
		});

		inventoryButton = new TextButton("Inventory", skin);
		inventoryButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				setInventoryTab();
			}
		});

		settingButton = new TextButton("Setting", skin);
		settingButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				setSettingTab();
			}
		});

		skillButton = new TextButton("Skills", skin);
		skillButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				setSkillTab();
			}
		});

		mapButton = new TextButton("Map", skin);
		mapButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				setMapTab();
			}
		});

		socialButton = new TextButton("Social", skin);
		socialButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				setSocialTab();
			}
		});

		missionButton = new TextButton("Missions", skin);
		missionButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				setMissionTab();
			}
		});

		int farmingLevel = player.getSkillLevel(SkillType.FARMING);
		int miningLevel = player.getSkillLevel(SkillType.MINING);
		int foragingLevel = player.getSkillLevel(SkillType.FORAGING);
		int fishingLevel = player.getSkillLevel(SkillType.FISHING);
		farmingSkillLabel = new Label("Farming", skin);
		farmingSkillLabel.setPosition(610, 700);
		for (int i = 0; i < 10; i++) {
			Image im;
			if (i < farmingLevel)
				im = new Image(redSquare);
			else
				im = new Image(whiteSquare);
			im.setPosition(750 + i * 60, 700);
			farmingLevels.add(im);
		}
		miningSkillLabel = new Label("Mining", skin);
		miningSkillLabel.setPosition(610, 600);
		for (int i = 0; i < 10; i++) {
			Image im;
			if (i < miningLevel)
				im = new Image(redSquare);
			else
				im = new Image(whiteSquare);
			im.setPosition(750 + i * 60, 600);
			miningLevels.add(im);
		}
		foragingSkillLabel = new Label("Foraging", skin);
		foragingSkillLabel.setPosition(610, 500);
		for (int i = 0; i < 10; i++) {
			Image im;
			if (i < foragingLevel)
				im = new Image(redSquare);
			else
				im = new Image(whiteSquare);
			im.setPosition(750 + i * 60, 500);
			foragingLevels.add(im);
		}
		fishingSkillLabel = new Label("Fishing", skin);
		fishingSkillLabel.setPosition(610, 400);
		for (int i = 0; i < 10; i++) {
			Image im;
			if (i < fishingLevel)
				im = new Image(redSquare);
			else
				im = new Image(whiteSquare);
			im.setPosition(750 + i * 60, 400);
			fishingLevels.add(im);
		}


		// aaaa1111@

		setInventoryTab();
	}

	public void show() {
		Gdx.input.setInputProcessor(stage);
	}

	public void draw() {
		if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
			System.out.println("Left mouse button clicked!, coor: " + Gdx.input.getX() + " " + Gdx.input.getY());
		}
		stage.draw();
	}

	public void dispose() {
		emptyTexture.dispose();
		sampleTexture.dispose();
	}

	private void setEmpty() {
		// empty-white square
		Pixmap pixmap = new Pixmap(50, 50, Pixmap.Format.RGBA8888);
		pixmap.setColor(1, 1, 1, 1);
		pixmap.fillRectangle(0, 0, 50, 50);
		emptyTexture = new Texture(pixmap);
		pixmap.dispose();
		emptyDrawable = new TextureRegionDrawable(new TextureRegion(emptyTexture));

//		whiteSquare, redSquare
		int size = 30;
		pixmap = new Pixmap(size, size, Pixmap.Format.RGBA8888);
		pixmap.setColor(1, 1, 1, 1);
		pixmap.fillRectangle(0, 0, size, size);
		whiteSquare = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));

		pixmap.setColor(1, 0, 0, 1);
		pixmap.fillRectangle(0, 0, size, size);
		redSquare = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
	}

	private void setNumberFont() {
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/font.otf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

		parameter.size = 24;
		parameter.color = Color.WHITE;
		parameter.borderWidth = 2;
		parameter.borderColor = Color.BLACK;
		parameter.borderStraight = true;

		numberFont = generator.generateFont(parameter);
		generator.dispose();
	}

	private void setDarkBackground() {
		Pixmap bgPixmap = new Pixmap((int) stage.getWidth(), (int) stage.getHeight(), Pixmap.Format.RGBA8888);
		bgPixmap.setColor(0, 0, 0, 0.5f);
		bgPixmap.fill();
		Texture bgTexture = new Texture(bgPixmap);
		bgPixmap.dispose();
//		tableDialog.setBackground(new TextureRegionDrawable(new TextureRegion(bgTexture)));
		stage.addActor(new Image(bgTexture));
	}

	private void setButtonBar() {
		setDarkBackground();
		Table buttonTable = new Table();
		buttonTable.add(inventoryButton);
		buttonTable.add(socialButton).pad(0);
		buttonTable.add(mapButton).pad(0);
		buttonTable.add(skillButton).pad(0);
		buttonTable.add(settingButton).pad(0);
		buttonTable.add(missionButton).pad(0);

		buttonTable.add(exitButton).padLeft(100);
		buttonTable.setPosition(1000, 800);

		stage.addActor(buttonTable);
	}

	private void setInventoryTab() {
		stage.clear();

		setButtonBar();
		stage.addActor(inventoryScrollPane);
		stage.setScrollFocus(inventoryScrollPane);
	}

	private void setSkillTab() {
		stage.clear();
		setButtonBar();

		stage.addActor(farmingSkillLabel);
		for (Image i: farmingLevels) {
			stage.addActor(i);
		}
		stage.addActor(miningSkillLabel);
		for (Image i: miningLevels) {
			stage.addActor(i);
		}
		stage.addActor(foragingSkillLabel);
		for (Image i: foragingLevels) {
			stage.addActor(i);
		}
		stage.addActor(fishingSkillLabel);
		for (Image i: fishingLevels) {
			stage.addActor(i);
		}
	}


	private void setMapTab() {
//		tableDialog.getContentTable().clear();
		stage.clear();
		setButtonBar();
	}

	private void setSocialTab() {
//		tableDialog.getContentTable().clear();
		stage.clear();
		setButtonBar();

	}

	private void setMissionTab() {
//		tableDialog.getContentTable().clear();
		stage.clear();
		setButtonBar();
	}

	private void setSettingTab() {
//		tableDialog.getContentTable().clear();
		stage.clear();
		setButtonBar();
	}

	private String capitilize(Tool tool) {
		String type = tool.getToolMaterialType().toString();
		String name = tool.getToolType().toString();
		return type.substring(0, 1).toUpperCase() + type.substring(1).toLowerCase() + "_" + name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
	}
}