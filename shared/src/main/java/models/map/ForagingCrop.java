package models.map;

import Asset.SharedAssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import data.ForagingCropData;
import data.items.AllItemsData;
import models.Item.Item;
import models.Saver;
import models.crop.Harvestable;
import models.game.Player;
import models.sprite.GameSprite;
import models.time.Date;

import java.io.Serializable;

public class ForagingCrop implements Placable, Harvestable, Serializable, Saver {
	public ForagingCropData data;
	private transient GameSprite sprite;

	public ForagingCrop() {}

	public ForagingCrop(ForagingCropData data) {
		this.data = data;
		sprite = new GameSprite(AllItemsData.getData(getResultName()).getTextureAddress());
	}

	@Override
	public void load() {
	}

	@Override
	public void save() {

	}

	public Item harvest(Player player) {
		return Item.build(data.getName(), 1);
	}

	public ForagingCropData getData(Date date) {
		return data;
	}

	@Override
	public TileType getTileType() {
		return TileType.PLANTED_TREE;
	}

	@Override
	public boolean isWalkable() {
		return false;
	}

	@Override
	public String getSprite() {
		return "C";
	}

	@Override
	public String getTexture() {
		return null;
	}

	@Override
	public Sprite spriteGetter() {
		return sprite;
	}

	public String getResultName() {
		return data.getName();
	}

	public boolean isHarvestReady() {
		return true;
	}

	@Override
	public String toString() {
		return String.format(
				"Foraging Crop:\n\tName: %s",
				data.getName()
		);
	}
}
