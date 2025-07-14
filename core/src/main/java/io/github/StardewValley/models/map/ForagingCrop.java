package io.github.StardewValley.models.map;

import io.github.StardewValley.models.Item.Item;
import io.github.StardewValley.models.crop.Harvestable;
import io.github.StardewValley.data.ForagingCropData;

import java.io.Serializable;

public class ForagingCrop implements Placable, Harvestable, Serializable {
	public final ForagingCropData data;

	public ForagingCrop(ForagingCropData data) {
		this.data = data;
	}

	public Item harvest() {

		return Item.build(data.getName(), 1);
	}

	public ForagingCropData getData() {
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
