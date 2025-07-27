package io.github.StardewValley.views.menu.GUI;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import data.items.AllItemsData;

class InventoryTab {
	private Stage stage;
	private Skin skin;
	private GameScreen gameScreen;
	private Dialog tableDialog;
	private Texture emptyTexture;
	private TextureRegionDrawable emptyDrawable;
	private TextButton exitButton, inventoryButton, socialButton, mapButton, settingButton, skillButton, missionButton;
	private ScrollPane inventoryScrollPane;

	private int dialogWidth, dialogHeight;
	private Texture sampleTexture;

	public InventoryTab(GameScreen gameScreen, Stage stage, Skin skin) {
		this.gameScreen = gameScreen;
		this.stage = stage;
		this.skin = skin;

		setEmpty();

		sampleTexture = new Texture(AllItemsData.getData("Wool").getTextureAddress());

		// Create Dialog
		dialogWidth = Gdx.graphics.getWidth() / 2;
		dialogHeight = Gdx.graphics.getHeight() / 2;
		tableDialog = new Dialog("", skin) {
			@Override
			protected void result(Object object) {
				gameScreen.onInventoryClosed();
			}
		};
		tableDialog.setSize(dialogWidth, dialogHeight);
		tableDialog.setPosition(
				(Gdx.graphics.getWidth() - dialogWidth) / 2,
				(Gdx.graphics.getHeight() - dialogHeight) / 2
		);
		tableDialog.setModal(true);
		tableDialog.setMovable(false);

		setDarkBackground();

//		tableDialog.button("exit");
		// ایجاد جدول برای اسکرول
		Table scrollTable = new Table();

		scrollTable.top();
		scrollTable.defaults().width(64).height(64).pad(2);

		for (int row = 0; row < 70; row++) {
			for (int col = 0; col < 12; col++) {
				Image cell;
				if ((row - col + 100) % 5 == 0) { // سطر 2، ستون 3
					cell = new Image(new TextureRegionDrawable(new TextureRegion(sampleTexture)));
				} else {
					cell = new Image(emptyDrawable);
				}
				scrollTable.add(cell);
			}
			scrollTable.row();
		}

		// تنظیم ScrollPane
		inventoryScrollPane = new ScrollPane(scrollTable, skin);
		inventoryScrollPane.setScrollingDisabled(true, false);
		inventoryScrollPane.setFlickScroll(true);
		inventoryScrollPane.setSmoothScrolling(true);
		inventoryScrollPane.setForceScroll(false, false);
		inventoryScrollPane.setOverscroll(false, false);
		inventoryScrollPane.setFadeScrollBars(false);
		inventoryScrollPane.setSize(1000, 100); // ارتفاع برای 3 سطر

		inventoryScrollPane.addListener(new InputListener() {
			@Override
			public boolean scrolled(InputEvent event, float x, float y, float amountX, float amountY) {
				System.out.println("Scroll detected: " + amountY);
				return super.scrolled(event, x, y, amountX, amountY);
			}
		});

		exitButton = new TextButton("Exit", skin);
		exitButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				tableDialog.cancel();
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
		// aaaa1111@

		setInventoryTab();
	}

	public void show() {
		tableDialog.show(stage);
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
	}

	private void setDarkBackground() {
		Pixmap bgPixmap = new Pixmap((int) stage.getWidth(), (int) stage.getHeight(), Pixmap.Format.RGBA8888);
		bgPixmap.setColor(0, 0, 0, 0.5f);
		bgPixmap.fill();
		Texture bgTexture = new Texture(bgPixmap);
		bgPixmap.dispose();
		tableDialog.setBackground(new TextureRegionDrawable(new TextureRegion(bgTexture)));
	}

	private void setButtonBar() {
		Table buttonTable = new Table();
		buttonTable.add(inventoryButton);
		buttonTable.add(socialButton).pad(0);
		buttonTable.add(mapButton).pad(0);
		buttonTable.add(skillButton).pad(0);
		buttonTable.add(settingButton).pad(0);
		buttonTable.add(missionButton).pad(0);

		buttonTable.add(exitButton).padLeft(40);

		tableDialog.getContentTable().add(buttonTable);
		tableDialog.getContentTable().row();
//		tableDialog.getContentTable().setSize(1600, 1000);
	}

	private void setInventoryTab() {
		tableDialog.getContentTable().clear();
		setButtonBar();
		tableDialog.getContentTable().add(inventoryScrollPane).width(850).height(210);
		stage.setScrollFocus(inventoryScrollPane);
	}

	private void setSkillTab() {
		tableDialog.getContentTable().clear();
		setButtonBar();
	}

	private void setMapTab() {
		tableDialog.getContentTable().clear();
		setButtonBar();
	}

	private void setSocialTab() {
		tableDialog.getContentTable().clear();
		setButtonBar();

	}

	private void setMissionTab() {
		tableDialog.getContentTable().clear();
		setButtonBar();

	}

	private void setSettingTab() {
		tableDialog.getContentTable().clear();
		setButtonBar();

	}
}