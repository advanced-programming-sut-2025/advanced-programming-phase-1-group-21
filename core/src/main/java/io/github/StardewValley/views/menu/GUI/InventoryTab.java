package io.github.StardewValley.views.menu.GUI;

import Asset.SharedAssetManager;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import data.items.AllItemsData;
import data.items.RecipeData;
import io.github.StardewValley.controllers.GameController;
import models.Item.Item;
import models.Item.ItemType;
import models.Item.Recipe;
import models.Item.RecipeType;
import models.game.*;
import models.skill.SkillType;
import models.tool.Tool;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

class InventoryTab {
	private final Player player;
	private final Stage stage;
	private final Skin skin;
	private final GameScreen gameScreen;
	private final GameController gc;
	private Texture emptyTexture;
	private TextureRegionDrawable emptyDrawable, whiteSquare, redSquare;
	private TextButton exitButton, inventoryButton, socialButton, mapButton, settingButton, skillButton, missionButton, craftButton, cookButton;
	private Item goldItem, trashcanItem;
	private Label goldLabel;
	private Stack itemInHandStack, trashcanStack;
	private Table skillTable, hoverTable, socialTitle, missionTitle;

	private Container<Image> inventorySelectedItem, craftingSelectedItem, cookingSelectedItem;

	private Image mapImage;

	private ScrollPane inventoryScrollPane, craftingScrollPane, cookingScrollPane, socialScrollPane, missionScrollPane;
	BitmapFont numberFont;

	public InventoryTab(Player player, GameScreen gameScreen, GameController gc, Skin skin) {
		this.player = player;
		stage = new Stage(new ScreenViewport());
		this.gameScreen = gameScreen;
		this.gc = gc;
		this.skin = skin;

		Inventory inv = player.getInventory();
		inv.addItem(Item.build("Loom", 1));
		inv.addItem(Item.build("Bee House", 1));
		inv.addItem(Item.build("Furnace", 15));
		inv.addItem(Item.build("Keg", 1));
		inv.addItem(Item.build("Wool", 3));
		inv.addItem(Item.build("Coal", 8));
		inv.addItem(Item.build("Stone", 2000));
		inv.addItem(Item.build("Wood", 200));
		inv.addItem(Item.build("Copper Bar", 100));
		inv.addItem(Item.build("Iron Bar", 2));
		inv.addItem(Item.build("Jazz Seeds", 5));
		inv.addItem(Item.build("Banana Sapling", 5));
		inv.addItem(Item.build("Bean Starter", 5));
		inv.addItem(Item.build("Deluxe Retaining Soil", 5));
		inv.addItem(Item.build("Egg", 5));

		player.addRecipes(new Recipe(RecipeData.getCookingRecipeData("Dish O' The Sea Recipe"), RecipeType.COOKING, 1));
		player.addRecipes(new Recipe(RecipeData.getCookingRecipeData("Triple Shot Espresso Recipe"), RecipeType.COOKING, 1));
		player.addRecipes(new Recipe(RecipeData.getCookingRecipeData("Tortilla Recipe"), RecipeType.COOKING, 1));
		player.addRecipes(new Recipe(RecipeData.getCraftingRecipeData("Charcoal Kiln Recipe"), RecipeType.CRAFTING, 1));
		player.addRecipes(new Recipe(RecipeData.getCraftingRecipeData("Bee House Recipe"), RecipeType.CRAFTING, 1));
		player.addRecipes(new Recipe(RecipeData.getCraftingRecipeData("Keg Recipe"), RecipeType.CRAFTING, 1));
	}

	void createUI() {
		stage.clear();
		setEmpty();
		setNumberFont();

		createMainButtonsUI();
		createInventoryUI();
		createSkillsUI();
		createSocialUI();
		createCraftingUI();
		createCookingUI();
		createMapUI();
		createMissionUI();
	}

	private void createMainButtonsUI() {
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

		craftButton = new TextButton("Crafting", skin);
		craftButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				setCraftTab();
			}
		});

		cookButton = new TextButton("Cooking", skin);
		cookButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				setCookTab();
			}
		});
	}

	private void createInventoryUI() {
		Table scrollTable = new Table();
		ArrayList<ArrayList<Item>> itemMatrix = new ArrayList<>();
		scrollTable.defaults().width(64).height(64).pad(2);
		Inventory inv = player.getInventory();
		List<Item> items = inv.getItems();
		int rowIndex = 0, colIndex = 0, number = 0;
		for (Item item: items) {
			if (item.getName().equalsIgnoreCase("coin")) {
				goldItem = item;
				continue;
			}
			if (item.getName().equals("TRASHCAN")) {
				trashcanItem = item;
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

		int rowNumber = inv.getMaximumSize() / 12;
		if (inv.getInventoryType() == InventoryType.UNLIMITED)
			rowNumber = Integer.max(3, (items.size() + 11) / 12);

		for (int row = 0; row < rowNumber; row++) {
			for (int col = 0; col < 12; col++) {
				Image cell;
				int numberOfItem = -1;
				if (row * 12 + col < number) {
					Item item = itemMatrix.get(row).get(col);
					numberOfItem = item.getAmount();
					AllItemsData data = AllItemsData.getData(item.getName());
					if (data != null) {
						String address = data.getTextureAddress();
						if (address != null)
							cell = new Image(new TextureRegionDrawable(new TextureRegion(SharedAssetManager.getOrLoad(address))));
						else
							cell = new Image(emptyDrawable);
						cell.setUserObject(item);
					}
					else {
						if (item.getItemType() == ItemType.TOOL) {
							cell = new Image(new TextureRegionDrawable(new TextureRegion(SharedAssetManager.getOrLoad(getItemTexture(item)))));
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
				Container<Image> container = new Container<>();
				container.setSize(64, 64);
				container.setActor(cell);
				container.addListener(new ClickListener() {
					@Override
					public void clicked(InputEvent event, float x, float y) {
						if (inventorySelectedItem != null)
							inventorySelectedItem.setBackground(skin.newDrawable("white", Color.CLEAR));

						container.setBackground(skin.newDrawable("white", new Color(Color.LIGHT_GRAY.r, Color.LIGHT_GRAY.g, Color.LIGHT_GRAY.b, 0.4f)));
						inventorySelectedItem = container;

						if (getTapCount() == 2) {
							Object o = container.getActor().getUserObject();
							if (o instanceof Item) {
								gc.setItemInHand((Item) o);
							}
							else {
								gc.setItemInHand(null);
							}
							setItemInHand();
						}
					}
				});

				if (numberOfItem != -1) {
					scrollTable.add(mergeActorAndNumber(container, numberOfItem));
				}
				else {
					Stack stack = new Stack();
					stack.add(container);
					scrollTable.add(stack);
				}

			}
			scrollTable.row();
		}

		inventoryScrollPane = new ScrollPane(scrollTable, skin);
		inventoryScrollPane.setScrollingDisabled(true, false);
		inventoryScrollPane.setFadeScrollBars(false);
		inventoryScrollPane.setSize(850, 200);
		inventoryScrollPane.setPosition(482, 575);
	}

	private void createCraftingUI() {
		Table scrollTable = new Table();
		scrollTable.defaults().width(64).height(64).pad(2);
		List<Recipe> recipes = player.getRecipes(RecipeType.CRAFTING);
		int rowNumber = Integer.max(3, (recipes.size() + 11) / 12);

		for (int row = 0; row < rowNumber; row++) {
			for (int col = 0; col < 12; col++) {
				Image cell;
				if (row * 12 + col < recipes.size()) {
					Recipe recipe = recipes.get(row * 12 + col);
					String resultName = recipe.getResult().getName();
					AllItemsData data = AllItemsData.getData(resultName);
					if (data != null) {
						String address = data.getTextureAddress();
						if (address != null) {
							cell = new Image(new TextureRegionDrawable(new TextureRegion(SharedAssetManager.getOrLoad(address))));
							cell.setUserObject(recipe);
						}
						else {
//							System.err.println("There is no texture for this item!! item name: " + resultName + ", row: " + row + ", col: " + col);
							cell = new Image(emptyDrawable);
							cell.setUserObject(recipe);
						}
					}
					else {
						cell = new Image(emptyDrawable);
						cell.setUserObject(null);
					}
				}
				else {
					cell = new Image(emptyDrawable);
					cell.setUserObject(null);
				}
				Container<Image> container = new Container<>();
				container.setSize(64, 64);
				container.setActor(cell);
				container.addListener(new ClickListener() {
					@Override
					public void clicked(InputEvent event, float x, float y) {
						if (craftingSelectedItem != null)
							craftingSelectedItem.setBackground(skin.newDrawable("white", Color.CLEAR));

						container.setBackground(skin.newDrawable("white", new Color(Color.LIGHT_GRAY.r, Color.LIGHT_GRAY.g, Color.LIGHT_GRAY.b, 0.4f)));
						craftingSelectedItem = container;

						if (getTapCount() == 2) {
							Object o = container.getActor().getUserObject();
							if (o instanceof Recipe) {
								craftDialog((Recipe) o);
								// It's show time ;))
							}
						}
					}
				});
				scrollTable.add(container);
			}
			scrollTable.row();
		}

		craftingScrollPane = new ScrollPane(scrollTable, skin);
		craftingScrollPane.setScrollingDisabled(true, false);
		craftingScrollPane.setFadeScrollBars(false);
		craftingScrollPane.setSize(850, 200);
		craftingScrollPane.setPosition(482, 575);
	}

	private void createCookingUI() {
		Table scrollTable = new Table();
		scrollTable.defaults().width(64).height(64).pad(2);
		List<Recipe> recipes = player.getRecipes(RecipeType.COOKING);
		int rowNumber = Integer.max(3, (recipes.size() + 11) / 12);

		for (int row = 0; row < rowNumber; row++) {
			for (int col = 0; col < 12; col++) {
				Image cell;
				if (row * 12 + col < recipes.size()) {
					Recipe recipe = recipes.get(row * 12 + col);
					String resultName = recipe.getResult().getName();
					AllItemsData data = AllItemsData.getData(resultName);
					if (data != null) {
						String address = data.getTextureAddress();
						if (address != null) {
							cell = new Image(new TextureRegionDrawable(new TextureRegion(SharedAssetManager.getOrLoad(address))));
							cell.setUserObject(recipe);
						}
						else {
//							System.err.println("There is no texture for this item!! item name: " + resultName + ", row: " + row + ", col: " + col);
							cell = new Image(emptyDrawable);
							cell.setUserObject(recipe);
						}
					}
					else {
						cell = new Image(emptyDrawable);
						cell.setUserObject(null);
					}
				}
				else {
					cell = new Image(emptyDrawable);
					cell.setUserObject(null);
				}
				Container<Image> container = new Container<>();
				container.setSize(64, 64);
				container.setActor(cell);
				container.addListener(new ClickListener() {
					@Override
					public void clicked(InputEvent event, float x, float y) {
						if (cookingSelectedItem != null)
							cookingSelectedItem.setBackground(skin.newDrawable("white", Color.CLEAR));

						container.setBackground(skin.newDrawable("white", new Color(Color.LIGHT_GRAY.r, Color.LIGHT_GRAY.g, Color.LIGHT_GRAY.b, 0.4f)));
						cookingSelectedItem = container;

						if (getTapCount() == 2) {
							Object o = container.getActor().getUserObject();
							if (o instanceof Recipe) {
								craftDialog((Recipe) o);
							}
						}
					}
				});
				scrollTable.add(container);
			}
			scrollTable.row();
		}

		cookingScrollPane = new ScrollPane(scrollTable, skin);
		cookingScrollPane.setScrollingDisabled(true, false);
		cookingScrollPane.setFadeScrollBars(false);
		cookingScrollPane.setSize(850, 200);
		cookingScrollPane.setPosition(482, 575);
	}

	private void createSkillsUI() {
		hoverTable = new Table();
		hoverTable.setSize(400, 100);
		hoverTable.setBackground(new TextureRegionDrawable(new TextureRegion(createBackGround(Color.GRAY))));
		Label description = new Label("", skin);
		hoverTable.add(description).center();
		hoverTable.setVisible(false);

		skillTable = new Table();
		skillTable.setPosition(490, 370);
		skillTable.setBackground(new TextureRegionDrawable(new TextureRegion(createBackGround(Color.DARK_GRAY))));

		int farmingLevel = player.getSkillLevel(SkillType.FARMING);
		int miningLevel = player.getSkillLevel(SkillType.MINING);
		int foragingLevel = player.getSkillLevel(SkillType.FORAGING);
		int fishingLevel = player.getSkillLevel(SkillType.FISHING);
		Label farmingSkillLabel = new Label("Farming:", skin);
		farmingSkillLabel.addListener(new InputListener() {
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				description.setText("This is farming skill");
				hoverTable.setVisible(true);
				updateHoverTablePosition(Gdx.input.getX(), Gdx.input.getY());
			}

			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
				hoverTable.setVisible(false);
			}

			@Override
			public boolean mouseMoved(InputEvent event, float x, float y) {
				updateHoverTablePosition(Gdx.input.getX(), Gdx.input.getY());
				return true;
			}
		});
		skillTable.add(farmingSkillLabel).pad(30).padRight(50);
		for (int i = 0; i < 10; i++) {
			Image im;
			if (i < farmingLevel)
				im = new Image(redSquare);
			else
				im = new Image(whiteSquare);
			skillTable.add(im).padRight(30);
		}
		skillTable.row();

		Label miningSkillLabel = new Label("Mining:", skin);
		miningSkillLabel.addListener(new InputListener() {
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				description.setText("This is mining skill");
				hoverTable.setVisible(true);
				updateHoverTablePosition(Gdx.input.getX(), Gdx.input.getY());
			}

			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
				hoverTable.setVisible(false);
			}

			@Override
			public boolean mouseMoved(InputEvent event, float x, float y) {
				updateHoverTablePosition(Gdx.input.getX(), Gdx.input.getY());
				return true;
			}
		});
		skillTable.add(miningSkillLabel).pad(30).padRight(50);

		for (int i = 0; i < 10; i++) {
			Image im;
			if (i < miningLevel)
				im = new Image(redSquare);
			else
				im = new Image(whiteSquare);
			skillTable.add(im).padRight(30);
		}
		skillTable.row();

		Label foragingSkillLabel = new Label("Foraging:", skin);
		foragingSkillLabel.addListener(new InputListener() {
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				description.setText("This is foraging skill");
				hoverTable.setVisible(true);
				updateHoverTablePosition(Gdx.input.getX(), Gdx.input.getY());
			}

			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
				hoverTable.setVisible(false);
			}

			@Override
			public boolean mouseMoved(InputEvent event, float x, float y) {
				updateHoverTablePosition(Gdx.input.getX(), Gdx.input.getY());
				return true;
			}
		});
		skillTable.add(foragingSkillLabel).pad(30).padRight(50);
		for (int i = 0; i < 10; i++) {
			Image im;
			if (i < foragingLevel)
				im = new Image(redSquare);
			else
				im = new Image(whiteSquare);
			skillTable.add(im).padRight(30);
		}
		skillTable.row();

		Label fishingSkillLabel = new Label("Fishing:", skin);
		fishingSkillLabel.addListener(new InputListener() {
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				description.setText("This is fishing skill");
				hoverTable.setVisible(true);
				updateHoverTablePosition(Gdx.input.getX(), Gdx.input.getY());
			}

			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
				hoverTable.setVisible(false);
			}

			@Override
			public boolean mouseMoved(InputEvent event, float x, float y) {
				updateHoverTablePosition(Gdx.input.getX(), Gdx.input.getY());
				return true;
			}
		});
		skillTable.add(fishingSkillLabel).pad(30).padRight(50);
		for (int i = 0; i < 10; i++) {
			Image im;
			if (i < fishingLevel)
				im = new Image(redSquare);
			else
				im = new Image(whiteSquare);
			skillTable.add(im).padRight(30);
		}

		skillTable.pack();
	}

	private void createSocialUI() {
		TextureRegionDrawable greenBox = getRectangle(Color.GREEN, 50, 30);
		TextureRegionDrawable whiteBox = getRectangle(Color.WHITE, 50, 30);

		Table socialTable = new Table();
		socialTable.setBackground(new TextureRegionDrawable(new TextureRegion(createBackGround(Color.DARK_GRAY))));

		for (NPC npc: ShowMap.listOfNPCs) {
			Table profile = new Table();
			Image profilePic = new Image(SharedAssetManager.getOrLoad("Textures/Villagers/" + npc.getName() + "_Profile.png"));
			profilePic.setSize(100, 100);
			profile.add(profilePic).padBottom(10).row();
			profile.add(new Label(npc.getName(), skin));
			profile.pack();

			Table levels = new Table();
			int level = npc.getFriendshipByPlayer(player).getLevel().getLevel();
			for (int i = 0; i < 4; i++) {
				Image im;
				if (i < level) {
					im = new Image(greenBox);
				}
				else {
					im = new Image(whiteBox);
				}
				levels.add(im).padRight(30);
			}
			levels.pack();

			socialTable.add(profile).pad(10);
			socialTable.add(levels).pad(10).row();
		}
		socialTable.pack();

		socialScrollPane = new ScrollPane(socialTable, skin);
		socialScrollPane.setScrollingDisabled(true, false);
		socialScrollPane.setFadeScrollBars(false);

		socialScrollPane.setSize(850, 400);
		socialScrollPane.setPosition(482, 275);


		socialTitle = new Table();
		socialTitle.setBackground(new TextureRegionDrawable(new TextureRegion(createBackGround(Color.DARK_GRAY))));
		socialTitle.setSize(850, 100);
		socialTitle.add(new Label("Villager", skin)).pad(50);
		socialTitle.add(new Label("Friendship", skin)).pad(100);
		socialTitle.setPosition(482, 375 + 300);
	}

	private void createMapUI() {
		int mapID = player.getMapID();
//		int mapID = 1;
		Texture t = SharedAssetManager.getOrLoad(	"Textures/map/Map" + mapID + ".png");
		if (t == null) {
			System.out.println("Textures/map/Map" + mapID + ".png");
			Texture t2 = new Texture("Textures/map/Map" + mapID + ".png");
			if (t2 == null) {
				System.out.println("Both");
			}
			else
				System.out.println("t2 good");
			mapImage = new Image(emptyDrawable);
		}
		else
			mapImage = new Image(SharedAssetManager.getOrLoad("Textures/map/Map" + mapID + ".png"));

		mapImage.setPosition(482, 275);
		mapImage.setSize(1000, 500);
	}

	private void createMissionUI() {

		Table missionTable = new Table();
		missionTable.setBackground(new TextureRegionDrawable(new TextureRegion(createBackGround(Color.DARK_GRAY))));

		for (NPC npc: ShowMap.listOfNPCs) {
			Table profile = new Table();
			Image profilePic = new Image(SharedAssetManager.getOrLoad("Textures/Villagers/" + npc.getName() + "_Profile.png"));
			profilePic.setSize(100, 100);
			profile.add(profilePic).padBottom(10).row();
			profile.add(new Label(npc.getName(), skin));
			profile.pack();

			Table missions = new Table();

			System.out.println("NPC: " + npc.getName());
			NPCFriendship npcFriendship = npc.getFriendshipByPlayer(player);
			for (int i = 0; i < 3; i++) {
				missions.add(new Label(npc.getTasks().get(i).getRequestItem() +
						": " + npc.getTasks().get(i).getRequestAmount(), skin)).left().pad(10).padLeft(30);
				missions.add(new Label(npc.getTasks().get(i).getRewardItem() +
						": " + npc.getTasks().get(i).getRewardAmount(), skin)).left().pad(10).padLeft(30);

				Label status = new Label("Unavailable", skin);
				status.setColor(Color.LIGHT_GRAY);
				if (i <= npcFriendship.getLevel().getLevel()) {
					if (npc.getTasksFlag().get(i)) {
						status.setText("Done");
						status.setColor(Color.GREEN);
					}
					else {
						status.setText("Available");
						status.setColor(Color.RED);
					}
				}
				missions.add(status).left().pad(10);
				missions.row();
			}
			missions.pack();

			missionTable.add(profile).pad(10);
			missionTable.add(missions).pad(10).row();
		}
		missionTable.pack();

		missionScrollPane = new ScrollPane(missionTable, skin);
		missionScrollPane.setScrollingDisabled(true, false);
		missionScrollPane.setFadeScrollBars(false);

		missionScrollPane.setSize(1050, 400);
		missionScrollPane.setPosition(482, 275);


		missionTitle = new Table();
		missionTitle.setBackground(new TextureRegionDrawable(new TextureRegion(createBackGround(Color.DARK_GRAY))));
		missionTitle.setSize(1050, 100);
		missionTitle.add(new Label("Villager", skin)).pad(50);
		missionTitle.add(new Label("Requested", skin)).pad(100);
		missionTitle.add(new Label("Reward", skin)).pad(100);
		missionTitle.add(new Label("Status", skin)).pad(100);
		missionTitle.setPosition(482, 375 + 300);
	}

	private void updateHoverTablePosition(int screenX, int screenY) {
		Vector3 pos = new Vector3(screenX, screenY, 0);
		stage.getViewport().unproject(pos);

		float tableX = pos.x - hoverTable.getWidth();
		float tableY = pos.y;

		hoverTable.setPosition(tableX, tableY);
	}

	private Texture createBackGround(Color color) {
		Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
		pixmap.setColor(color);
		pixmap.fill();
		Texture texture = new Texture(pixmap);
		pixmap.dispose();
		return texture;
	}

	public void show() {
		setInventoryTab();
		Gdx.input.setInputProcessor(stage);
	}

	public void draw() {
		if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
//			System.out.println("Left mouse button clicked!, coor: " + Gdx.input.getX() + " " + Gdx.input.getY());
		}
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}

	public void dispose() {
		stage.dispose();
		emptyTexture.dispose();
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
		pixmap.dispose();
	}

	private TextureRegionDrawable getRectangle(Color color, int width, int height) {
		Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);;
		pixmap.setColor(color);
		pixmap.fillRectangle(0, 0, width, height);
		TextureRegionDrawable result = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
		pixmap.dispose();
		return result;
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
		buttonTable.add(craftButton).pad(0);
		if (true) { // if player is in the house
			buttonTable.add(cookButton).pad(0);
		}

		buttonTable.add(exitButton).padLeft(100);
		buttonTable.setPosition(1000, 800);

		stage.addActor(buttonTable);
	}

	private void setInventoryTab() {
		setStage();
		setButtonBar();
		setTrashcan();
		addInventoryActors();
	}
	private void addInventoryActors() {
		stage.addActor(goldLabel);
		setItemInHand();
		stage.addActor(inventoryScrollPane);
	}

	private void setSkillTab() {
		setStage();
		setButtonBar();
		stage.addActor(skillTable);
		stage.addActor(hoverTable);
	}

	private void setMapTab() {
		setStage();
		setButtonBar();

		stage.addActor(mapImage);
	}

	private void setSocialTab() {
		setStage();
		setButtonBar();

		stage.addActor(socialTitle);
		stage.addActor(socialScrollPane);
	}

	private void setMissionTab() {
		setStage();
		setButtonBar();

		stage.addActor(missionTitle);
		stage.addActor(missionScrollPane);
	}

	private void setCraftTab() {
		setStage();
		setButtonBar();
		stage.addActor(craftingScrollPane);
	}

	private void setCookTab() {
		setStage();
		setButtonBar();
		stage.addActor(cookingScrollPane);
	}

	private void setSettingTab() {
		setStage();
		setButtonBar();
	}

	private String capitalize(Tool tool) {
		String type = tool.getToolMaterialType().toString();
		String name = tool.getToolType().toString();
		return type.substring(0, 1).toUpperCase() + type.substring(1).toLowerCase() + "_" + name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
	}

	private String getItemTexture(Item item) {
		if (item == null)
			return null;
		if (item.getName().equalsIgnoreCase("coin"))
			return null;
		if (item.getItemType() == ItemType.TOOL)
			return "Textures/Tools/" + capitalize(((Tool) item)) + ".png";
		AllItemsData data = AllItemsData.getData(item.getName());
		if (data == null)
			return null;
		return data.getTextureAddress();
	}

	private void setTrashcan() {
		if (trashcanStack != null)
			trashcanStack.remove();
		Image trashcanImage = new Image(new TextureRegionDrawable(new TextureRegion(SharedAssetManager.getOrLoad(getItemTexture(trashcanItem)))));
		trashcanStack = new Stack();
		trashcanStack.add(trashcanImage);
		trashcanStack.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (inventorySelectedItem == null || !(inventorySelectedItem.getActor().getUserObject() instanceof Item item) || item.getItemType() == ItemType.TOOL)
					return;

				int amount = 1;
				if (getTapCount() == 2) {

					amount = item.getAmount();
					System.out.println("TEST");
				}
				gc.removeFromInventory(item.getName(), amount);
				inventoryScrollPane.remove();
				goldLabel.remove();
				createInventoryUI();
				addInventoryActors();
			}
		});

		trashcanStack.setPosition(1350, 580);
		trashcanStack.setSize(64, 64);
		stage.addActor(trashcanStack);
	}

	private void setItemInHand() {
		if (itemInHandStack != null)
			itemInHandStack.remove();
		Item item = player.getItemInHand();
		Image itemInHandImage;
		if (item == null || getItemTexture(item) == null) {
			itemInHandImage = new Image(emptyDrawable);
			itemInHandStack = new Stack();
			itemInHandStack.add(itemInHandImage);
		}
		else {
			itemInHandImage = new Image(new TextureRegionDrawable(new TextureRegion(SharedAssetManager.getOrLoad(getItemTexture(item)))));
			itemInHandStack = mergeActorAndNumber(itemInHandImage, item.getAmount());
		}

		itemInHandStack.setPosition(1350, 480);
		itemInHandStack.setSize(64, 64);
		stage.addActor(itemInHandStack);
	}

	private void craftDialog(Recipe recipe) {
		String titleName, buttonName;
		if (recipe.getType() == RecipeType.COOKING) {
			titleName = "Cooking";
			buttonName = "Cook";
		}
		else {
			titleName = "Crafting";
			buttonName = "Craft";
		}

		Label descriptionLabel = new Label(recipe.getName() + "\n" + recipe.getData().getDescription(), skin);
		descriptionLabel.setAlignment(Align.center);

		Table imageTable = new Table();
		Map<String, Integer> ingredients = recipe.getData().getIngredients();

		final Consumer<Integer> updateDialog = (amount) -> {
			imageTable.clearChildren();

			for (String ingredient : ingredients.keySet()) {
				Image ingredientImage = new Image(SharedAssetManager.getOrLoad(AllItemsData.getData(ingredient).getTextureAddress()));
				int value = amount * ingredients.get(ingredient);
				if (player.getInventory().canRemoveItem(Item.build(ingredient, value)))
					imageTable.add(mergeActorAndNumber(ingredientImage, value)).size(64, 64).padRight(10);
				else
					imageTable.add(mergeActorAndNumber(ingredientImage, value, Color.RED)).size(64, 64).padRight(10);
			}
		};
		updateDialog.accept(1);

		final TextField amountField = new TextField("1", skin);
		amountField.setTextFieldFilter(new TextField.TextFieldFilter.DigitsOnlyFilter());

		Table amountTable = new Table();
		TextButton minusButton = new TextButton("-", skin);
		TextButton plusButton = new TextButton("+", skin);

		minusButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				int amount = Integer.parseInt(amountField.getText());
				if(amount > 1) amount--;
				amountField.setText(String.valueOf(amount));
				updateDialog.accept(amount);
			}
		});

		plusButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				int amount = Integer.parseInt(amountField.getText());
				amount++;
				amountField.setText(String.valueOf(amount));
				updateDialog.accept(amount);
			}
		});

		amountTable.add(minusButton).padRight(5);
		amountTable.add(amountField).width(60);
		amountTable.add(plusButton).padLeft(5);


		Dialog d = new Dialog(titleName, skin) {
			@Override
			public Dialog show(Stage stage) {
				super.show(stage);
				getTitleLabel().setAlignment(Align.center);
				getTitleTable().getCell(getTitleLabel()).expandX().fillX();
				return this;
			}
			@Override
			protected void result(Object object) {
				try {
					String result = (String) object;
					if (result.equals("Done")) {
						craft(recipe, Integer.parseInt(amountField.getText()));
					}
				}
				catch (Exception e) {}
			}
		};
		d.button(buttonName, "Done");
		d.button("Cancel", "Cancel");
		d.setSize(400, 300);
		d.setModal(true);

		Table contentTable = d.getContentTable();
		contentTable.defaults().pad(10);
		contentTable.add(descriptionLabel).colspan(2).row();
		contentTable.add(imageTable).colspan(2).row();
		contentTable.add(amountTable).colspan(2).row();

		d.show(stage);
	}

	private void craft(Recipe recipe, int amount) {
		ArrayList <Item> items = new ArrayList<>();
		Map <String, Integer> ingredients = recipe.getData().getIngredients();
		for (String itemName: ingredients.keySet()) {
			items.add(Item.build(itemName, ingredients.get(itemName) * amount));
		}
		if (player.getInventory().canRemoveItems(items)) {
			if (player.getInventory().canAdd(recipe.getData().getResultName())) {
				gc.removeItems(items);
				gc.addItem(Item.build(recipe.getData().getResultName(), amount));
			}
			else {
				notEnoughSpaceInInventoryDialog().show(stage);
			}
		}
		else {
			notEnoughItemDialog().show(stage);
		}
	}

	private Dialog notEnoughItemDialog() {
		Dialog d = new Dialog("You don't have all the ingredients to make this item.", skin);
		d.button("Ok");
		return d;
	}

	private Dialog notEnoughSpaceInInventoryDialog() {
		Dialog d = new Dialog("You don't have space in your inventory for new item.", skin);
		d.button("Ok");
		return d;
	}

	private Stack mergeActorAndNumber(Actor actor, int number) {
		return mergeActorAndNumber(actor, number, Color.WHITE);
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
}