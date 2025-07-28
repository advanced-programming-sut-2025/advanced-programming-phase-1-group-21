package io.github.StardewValley.views.menu.GUI;

import com.badlogic.gdx.*;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
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
import models.network.Lobby;
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
	private final ArrayList<Image> farmingLevels, miningLevels, foragingLevels, fishingLevels;
	private final ArrayList<ArrayList<Item>> itemMatrix = new ArrayList<>();
	private Item goldItem;
	private Label goldLabel;
	private Image itemInHandImage;

	private Container<Image> selectedItem;


	private Table scrollTable;
	BitmapFont numberFont;

	private Texture sampleTexture;

	public InventoryTab(GameScreen gameScreen, Skin skin) {
		stage = new Stage(new ScreenViewport());
		this.gameScreen = gameScreen;
		this.skin = skin;
		farmingLevels = new ArrayList<>();
		miningLevels = new ArrayList<>();
		foragingLevels = new ArrayList<>();
		fishingLevels = new ArrayList<>();
		createUI();
	}

	void createUI() {
		stage.clear();
		Player player = App.getInstance().game.getCurrentPlayer();
		setEmpty();
		setNumberFont();
		stage.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				System.out.println("Stage click: " + x + ", " + y);
			}
		});

		sampleTexture = new Texture(AllItemsData.getData("Wool").getTextureAddress());

		scrollTable = new Table();
		scrollTable.defaults().width(64).height(64).pad(2);


		List<Item> items = player.getInventory().getItems();
		int rowIndex = 0, colIndex = 0, number = 0;
		for (Item item: items) {
			if (item.getName().equalsIgnoreCase("coin")) {
				goldItem = item;
				continue;
			}
			if (colIndex == 0)
				itemMatrix.add(new ArrayList<>());

			itemMatrix.get(rowIndex).add(item);
			colIndex++;

			if (colIndex == 12) {
				rowIndex++;
				colIndex = 0;
			}
			number++;
		}

		if (goldItem != null)
			goldLabel = new Label(goldItem.getAmount() + "g", skin);
		else
			goldLabel = new Label("Gold Item not found!", skin);
		goldLabel.setPosition(610, 500);

		setItemInHand();

		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 12; col++) {
				Container<Image> container = new Container<>();
				container.setSize(64, 64);
				Image cell;
				if (row * 12 + col < number) {
					Item item = itemMatrix.get(row).get(col);
					AllItemsData data = AllItemsData.getData(item.getName());
					if (data != null) {
						String address = data.getTextureAddress();
						if (address != null) {
							cell = new Image(new TextureRegionDrawable(new TextureRegion(new Texture(address))));
							cell.setUserObject(item);
						}
						else {
							System.err.println("There is no texture for this item!! item name: " + item.getName() + ", row: " + row + ", col: " + col);
							cell = new Image(emptyDrawable);
							cell.setUserObject(item);
						}
					}
					else {
						if (item.getItemType() == ItemType.TOOL) {
							cell = new Image(new TextureRegionDrawable(new TextureRegion(new Texture(getItemTexture(item)))));
							cell.setUserObject(item);
						}
						else {
							cell = new Image(emptyDrawable);
							cell.setUserObject(null);
						}
					}
				}
				else {
					cell = new Image(emptyDrawable);
					cell.setUserObject(null);
				}
				container.setActor(cell);
				container.addListener(new ClickListener() {
					@Override
					public void clicked(InputEvent event, float x, float y) {
						System.out.println("You clicked on a container");
						if (selectedItem != null)
							selectedItem.setBackground(skin.newDrawable("white", Color.CLEAR));

						container.setBackground(skin.newDrawable("white", new Color(Color.LIGHT_GRAY.r, Color.LIGHT_GRAY.g, Color.LIGHT_GRAY.b, 0.4f)));
						selectedItem = container;

						if (getTapCount() == 2) {
							Object o = container.getActor().getUserObject();
							if (o instanceof Item) {
								player.setItemInHand((Item) o);
								setItemInHand();
								System.out.println("changing ItemInHand");
							}
							else {
								System.err.println("There is no Item in here???");
							}
						}
					}
				});

				scrollTable.add(container);
			}
			scrollTable.row();
		}
		scrollTable.setSize(850, 200);
		scrollTable.setPosition(610, 575);

		scrollTable.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				System.out.println("you clicked on the scroll table");
			}
		});

		exitButton = new TextButton("X", skin);
		exitButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
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

		System.out.println("scrollTable bounds: x=" + scrollTable.getX() + ", y=" + scrollTable.getY() + ", w=" + scrollTable.getWidth() + ", h=" + scrollTable.getHeight());
	}

	public void show() {
		setInventoryTab();
		Gdx.input.setInputProcessor(stage);
	}

	public void draw() {
		if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
			System.out.println("Left mouse button clicked!, coor: " + Gdx.input.getX() + " " + Gdx.input.getY());
		}
		stage.act(Gdx.graphics.getDeltaTime());
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

	private void setDarkBackground() {
		Pixmap bgPixmap = new Pixmap((int) stage.getWidth(), (int) stage.getHeight(), Pixmap.Format.RGBA8888);
		bgPixmap.setColor(0, 0, 0, 0.5f);
		bgPixmap.fill();
		Texture bgTexture = new Texture(bgPixmap);
		bgPixmap.dispose();
//		tableDialog.setBackground(new TextureRegionDrawable(new TextureRegion(bgTexture)));
		stage.addActor(new Image(bgTexture));
	}

	private void setStage() {
		stage.clear();
		createUI();
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
		setStage();
		setButtonBar();
		stage.addActor(goldLabel);
		stage.addActor(itemInHandImage);
		stage.addActor(scrollTable);
	}

	private void setSkillTab() {
		setStage();
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

	private void setItemInHand() {
		String itemInHandTextureAddress = getItemTexture(App.getInstance().game.getCurrentPlayer().getItemInHand());
		if (itemInHandImage != null) {
			itemInHandImage.remove();
		}
		if (itemInHandTextureAddress == null) {
			itemInHandImage = new Image(emptyDrawable);
		}
		else {
			itemInHandImage = new Image(new TextureRegionDrawable(new TextureRegion(new Texture(itemInHandTextureAddress))));
		}
		itemInHandImage.setPosition(1350, 480);
		stage.addActor(itemInHandImage);
	}


	private void setMapTab() {
		setStage();
		setButtonBar();
	}

	private void setSocialTab() {
		setStage();
		setButtonBar();

	}

	private void setMissionTab() {
		setStage();
		setButtonBar();
	}

	private void setSettingTab() {
		setStage();
		setButtonBar();
	}

	private String capitilize(Tool tool) {
		String type = tool.getToolMaterialType().toString();
		String name = tool.getToolType().toString();
		return type.substring(0, 1).toUpperCase() + type.substring(1).toLowerCase() + "_" + name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
	}

	private String getItemTexture(Item item) {
		if (item == null)
			return null;
		if (item.getName().equalsIgnoreCase("coin"))
			return null;
		if (item.getItemType() == ItemType.TOOL) {
			return "Textures/Tools/" + capitilize(((Tool) item)) + ".png";
		}
		AllItemsData data = AllItemsData.getData(item.getName());
		if (data == null) {
			return null;
		}
		return data.getTextureAddress();
	}
}