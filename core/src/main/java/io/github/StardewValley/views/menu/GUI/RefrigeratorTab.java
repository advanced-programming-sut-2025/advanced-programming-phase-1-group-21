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
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import data.items.AllItemsData;
import io.github.StardewValley.controllers.GameController;
import models.Item.Item;
import models.Item.ItemType;
import models.game.Inventory;
import models.game.InventoryType;
import models.game.Player;
import models.game.Refrigerator;
import models.map.Coord;

import java.util.ArrayList;

public class RefrigeratorTab {
	private final Player player;
	private GameScreen gameScreen;
	private GameController gc;
	private Refrigerator ref;
	private Coord coord;

	private Skin skin;
	private Stage stage;
	private TextureRegionDrawable emptyDrawable;
	private Container<Image> inventorySelectedItem;
	BitmapFont numberFont;

	public RefrigeratorTab(Player player, GameScreen gameScreen, Skin skin) {
		this.player = player;
		this.gameScreen = gameScreen;
		this.skin = skin;
		stage = new Stage(new ScreenViewport());

		setNumberFont();
		setEmpty();
	}

	private void createUI() {
		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);
		Table mainTable = new Table(), inventoryTable = new Table(), refTable = new Table();
		mainTable.setBackground(skin.newDrawable("white", Color.LIGHT_GRAY));

		Label invLabel = new Label("Inventory", skin);
		Label refLabel = new Label("Refrigerator", skin);

		ScrollPane inventoryScrollPane = createInventoryUI(player.getInventory(), 1);
		inventoryTable.add(invLabel).pad(20).left().row();
		inventoryTable.add(inventoryScrollPane).pad(20);

		ScrollPane refScrollPane = createInventoryUI(ref.getInventory(), 2);
		refTable.add(refLabel).pad(20).left().row();
		refTable.add(refScrollPane).pad(20);

		TextButton closeButton = new TextButton("X", skin);
		closeButton.setColor(Color.RED);
		closeButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				stage.dispose();
				gameScreen.onRefTabClosed();
			}
		});
		mainTable.add(inventoryTable).pad(20).row();
		mainTable.add(refTable).pad(20).row();
		mainTable.add(closeButton).pad(20).row();
		mainTable.pack();

		mainTable.setPosition((Gdx.graphics.getWidth() - mainTable.getWidth()) / 2, (Gdx.graphics.getHeight() - mainTable.getHeight()) / 2);
		stage.addActor(mainTable);
	}

	public void show(Coord c, GameController gc) {
		this.gc = gc;
		coord = c;
		ref = player.getMap().getTile(c).getPlacable(Refrigerator.class);

		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);
		createUI();
	}

	public void draw() {
		if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
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

	private ScrollPane createInventoryUI(Inventory inv, int method) {
		Table scrollTable = new Table();
		ArrayList<ArrayList<Item>> itemMatrix = new ArrayList<>();
		scrollTable.defaults().width(64).height(64).pad(2);
		java.util.List<Item> items = inv.getItems();
		int rowIndex = 0, colIndex = 0, number = 0;
		for (Item item: items) {
			if (item.getItemType() != ItemType.CONSUMABLE)
				continue;
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
							cell = new Image(new TextureRegionDrawable(new TextureRegion(SharedAssetManager.getOrLoad(InventoryTab.getItemTexture(item)))));
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

						container.setBackground(skin.newDrawable("white", new Color(Color.DARK_GRAY.r, Color.DARK_GRAY.g, Color.DARK_GRAY.b, 0.4f)));
						inventorySelectedItem = container;

						if (getTapCount() == 2) {
							Object o = container.getActor().getUserObject();
							if (o instanceof Item item) {
								doMethod(item, method);
							}
						}
					}
				});

				if (numberOfItem != -1) {
					scrollTable.add(mergeActorAndNumber(container, numberOfItem, Color.WHITE));
				}
				else {
					Stack stack = new Stack();
					stack.add(container);
					scrollTable.add(stack);
				}
			}
			scrollTable.row();
		}

		ScrollPane inventoryScrollPane = new ScrollPane(scrollTable, skin);
		inventoryScrollPane.setScrollingDisabled(true, false);
		inventoryScrollPane.setFadeScrollBars(false);
		inventoryScrollPane.setSize(850, 200);
		inventoryScrollPane.setPosition(482, 575);
		return inventoryScrollPane;
	}

	private void setEmpty() {
		// empty-white square
		Pixmap pixmap = new Pixmap(50, 50, Pixmap.Format.RGBA8888);
		pixmap.setColor(1, 1, 1, 1);
		pixmap.fillRectangle(0, 0, 50, 50);
		Texture emptyTexture = new Texture(pixmap);
		pixmap.dispose();
		emptyDrawable = new TextureRegionDrawable(new TextureRegion(emptyTexture));
	}

	private void doMethod(Item item, int method) {
		if (method == 1) {
			// From player to fridge
			if (ref.getInventory().canAdd(item.getName())) {
				gc.addToRefrigerator(coord, item.getName(), item.getAmount());
				gc.removeItem(item.getName(), item.getAmount());
			}
		}
		else {
			// From fridge to player
			if (player.getInventory().canAdd(item.getName())) {
				gc.addItem(item.getName(), item.getAmount());
				gc.removeFromRefrigerator(coord, item.getName(), item.getAmount());
			}
		}
		createUI();
	}
}