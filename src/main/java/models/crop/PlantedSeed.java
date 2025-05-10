package models.crop;

import models.Item.Consumable;
import models.Item.Item;
import models.Item.ItemType;
import models.data.items.SeedData;
import models.map.Placable;

public class PlantedSeed implements Placable {
	private FertilizerType fertilizerType = null; // not implemented yet.
	private int stage = 0;
	private int day = 1;
	private int lastHarvest = -1;
	private int waterStage = 2;
	SeedData seedData;

	public PlantedSeed(SeedData seedData) {
		this.seedData = seedData;
	}

	public void water() {
		// This method is supposed to be called by its tile.

		waterStage = 2;
	}

	public Item harvest() {
		// This method is supposed to be called by its tile.

		if (lastHarvest == -1) {
			if (stage == seedData.getStages().size()) {
				lastHarvest = day;
				return Item.build(seedData.getResultName(), 1);
			}
		}
		else if (day - lastHarvest > seedData.getRegrowthTime()) {
			lastHarvest = day;
			return Item.build(seedData.getResultName(), 1);
		}
		return null;
	}

	public boolean nextDay() {
		// This method is supposed to be called by its tile.

		waterStage--;
		if (waterStage < 0)
			return false;

		day++;
		stage = seedData.getStage(day);
		return true;
	}

	public boolean isOneTime() {
		return seedData.isOneTime();
	}

	@Override
	public boolean isWalkable() {
		return false;
	}

	@Override
	public String getSprite() {
		return "S";
	}
}