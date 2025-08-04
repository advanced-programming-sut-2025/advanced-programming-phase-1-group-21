package io.github.StardewValley.views.menu.GUI;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import data.items.AllItemsData;
import data.items.RecipeData;
import models.Item.Item;
import models.Item.ItemType;
import models.Item.Recipe;
import models.Item.RecipeType;
import models.game.Inventory;
import models.game.InventoryType;
import models.game.Player;
import models.skill.SkillType;
import models.tool.Tool;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

class InventoryTab {
	private final Player player;
	private Stage stage;
	private Skin skin;
	private GameScreen gameScreen;
	private Texture emptyTexture;
	private TextureRegionDrawable emptyDrawable, whiteSquare, redSquare;
	private TextButton exitButton, inventoryButton, socialButton, mapButton, settingButton, skillButton, missionButton, craftButton, cookButton;
	private Label farmingSkillLabel, miningSkillLabel, foragingSkillLabel, fishingSkillLabel;
	private final ArrayList<Image> farmingLevels, miningLevels, foragingLevels, fishingLevels;
	private final ArrayList<ArrayList<Item>> itemMatrix = new ArrayList<>();
	private Item goldItem;
	private Label goldLabel;
	private Stack itemInHandStack;

	private Container<Image> inventorySelectedItem, craftingSelectedItem, cookingSelectedItem;

	private ScrollPane inventoryScrollPane, craftingScrollPane, cookingScrollPane;
	BitmapFont numberFont;

//	private Texture sampleTexture;

	public InventoryTab(Player player, GameScreen gameScreen, Skin skin) {
		this.player = player;
		stage = new Stage(new ScreenViewport());
		this.gameScreen = gameScreen;
		this.skin = skin;
		farmingLevels = new ArrayList<>();
		miningLevels = new ArrayList<>();
		foragingLevels = new ArrayList<>();
		fishingLevels = new ArrayList<>();

		Inventory inv = player.getInventory();
		inv.addItem(Item.build("Loom", 1));
		inv.addItem(Item.build("Bee House", 1));
		inv.addItem(Item.build("Furnace", 15));
		inv.addItem(Item.build("Keg", 1));
		inv.addItem(Item.build("Wool", 3));
		inv.addItem(Item.build("Coal", 8));
		inv.addItem(Item.build("Wood", 200));
		inv.addItem(Item.build("Copper Bar", 100));
		inv.addItem(Item.build("Iron Bar", 2));
		inv.addItem(Item.build("Jazz Seeds", 5));
		inv.addItem(Item.build("Banana Sapling", 5));
		inv.addItem(Item.build("Bean Starter", 5));

		player.addRecipes(new Recipe(RecipeData.getCookingRecipeData("Dish O' The Sea Recipe"), RecipeType.COOKING, 1));
		player.addRecipes(new Recipe(RecipeData.getCookingRecipeData("Triple Shot Espresso Recipe"), RecipeType.COOKING, 1));
		player.addRecipes(new Recipe(RecipeData.getCookingRecipeData("Tortilla Recipe"), RecipeType.COOKING, 1));
		player.addRecipes(new Recipe(RecipeData.getCraftingRecipeData("Charcoal Kiln Recipe"), RecipeType.CRAFTING, 1));
		player.addRecipes(new Recipe(RecipeData.getCraftingRecipeData("Bee House Recipe"), RecipeType.CRAFTING, 1));
		player.addRecipes(new Recipe(RecipeData.getCraftingRecipeData("Keg Recipe"), RecipeType.CRAFTING, 1));

		createUI();
	}

	void createUI() {
		stage.clear();
		itemMatrix.clear();
		setEmpty();
		setNumberFont();
//		sampleTexture = new Texture(AllItemsData.getData("Wool").getTextureAddress());

		createMainButtonsUI();
		createInventoryUI();
		createSkillsUI();
		createCraftingUI();
		createCookingUI();
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
		itemMatrix.clear();
		scrollTable.defaults().width(64).height(64).pad(2);
		Inventory inv = player.getInventory();
		List<Item> items = inv.getItems();
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

		int rowNumber = inv.getMaximumSize() / 12;
		if (inv.getInventoryType() == InventoryType.UNLIMITED)
			rowNumber = Integer.max(3, (items.size() + 11) / 12);

		for (int row = 0; row < rowNumber; row++) {
			for (int col = 0; col < 12; col++) {
				Image cell;
				int numberOfItem = -1;
				if (row * 12 + col < number) {
					Item item = itemMatrix.get(row).get(col);
//					System.out.println(item.getName());
					numberOfItem = item.getAmount();
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
								player.setItemInHand((Item) o);
							}
							else {
								player.setItemInHand(null);
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
							cell = new Image(new TextureRegionDrawable(new TextureRegion(new Texture(address))));
							cell.setUserObject(recipe);
						}
						else {
							System.err.println("There is no texture for this item!! item name: " + resultName + ", row: " + row + ", col: " + col);
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
							cell = new Image(new TextureRegionDrawable(new TextureRegion(new Texture(address))));
							cell.setUserObject(recipe);
						}
						else {
							System.err.println("There is no texture for this item!! item name: " + resultName + ", row: " + row + ", col: " + col);
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
		emptyTexture.dispose();
//		sampleTexture.dispose();
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
		stage.addActor(goldLabel);
		setItemInHand();
		stage.addActor(inventoryScrollPane);
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

	private void setItemInHand() {

		if (itemInHandStack != null) {
			itemInHandStack.remove();
		}
		Item item = player.getItemInHand();
		Image itemInHandImage;
		if (item == null || getItemTexture(item) == null) {
			itemInHandImage = new Image(emptyDrawable);
			itemInHandStack = new Stack();
			itemInHandStack.add(itemInHandImage);
		}
		else {
			itemInHandImage = new Image(new TextureRegionDrawable(new TextureRegion(new Texture(getItemTexture(item)))));
			itemInHandStack = mergeActorAndNumber(itemInHandImage, item.getAmount());
//			itemInHandStack.add(itemInHandImage);
//			Label.LabelStyle labelStyle = new Label.LabelStyle(numberFont, Color.WHITE);
//			Label numberLabel = new Label(String.valueOf(item.getAmount()), labelStyle);
//
//			Container<Label> numberContainer = new Container<>(numberLabel);
//			numberContainer.bottom().right();
//			numberContainer.padBottom(5).padRight(5); // Add some padding
//			itemInHandStack.add(numberContainer);
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
			// Clear previous images
			imageTable.clearChildren();

			for (String ingredient : ingredients.keySet()) {
				Image ingredientImage = new Image(new Texture(AllItemsData.getData(ingredient).getTextureAddress()));
				int value = amount * ingredients.get(ingredient);
				if (player.getInventory().canRemoveItem(Item.build(ingredient, value))) {
					imageTable.add(mergeActorAndNumber(ingredientImage, value)).size(64, 64).padRight(10);
				}
				else {
					imageTable.add(mergeActorAndNumber(ingredientImage, value, Color.RED)).size(64, 64).padRight(10);
				}
			}
		};
		updateDialog.accept(1);

// Create amount selector
		final TextField amountField = new TextField("1", skin);
		amountField.setTextFieldFilter(new TextField.TextFieldFilter.DigitsOnlyFilter());

		Table amountTable = new Table();
		TextButton minusButton = new TextButton("-", skin);
		TextButton plusButton = new TextButton("+", skin);

// Add button functionality
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
				// Center the title label after showing
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
				player.getInventory().removeItems(items);
				player.getInventory().addItem(Item.build(recipe.getData().getResultName(), amount));
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