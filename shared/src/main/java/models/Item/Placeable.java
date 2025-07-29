package models.Item;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import data.items.ItemData;
import data.items.PlaceableData;
import models.map.Placable;
import models.map.TileType;

public class Placeable extends Item implements Placable {
	private final PlaceableData data;
	private final ItemType itemType;
	private int amount;

	public Placeable(PlaceableData data, ItemType itemType, int amount) {
		this.data = data;
		this.itemType = itemType;
		this.amount = amount;
	}

	@Override
	public String getName() {
		return data.getName();
	}

	@Override
	public ItemType getItemType() {
		return itemType;
	}

	private ItemData getData() {
		return data;
	}

	@Override
	public int getAmount() {
		return amount;
	}

	@Override
	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getLocation() {
		return data.getLocation();
	}

	@Override
	public boolean isSalable() {
		return false;
	}

	@Override
	public int getPrice() {
		return 0;
	}

	@Override
	public void changeAmount(int change) {
		amount += change;
	}

	@Override
	public TileType getTileType() {
		return TileType.PLACEABLE;
	}

	@Override
	public boolean isWalkable() {
		return false;
	}

	@Override
	public String getSprite() {
		return "P";
	}

	@Override
	public Texture getTexture() {
		return null;
	}

	@Override
	public Sprite spriteGetter() {
		return null;
	}
}
