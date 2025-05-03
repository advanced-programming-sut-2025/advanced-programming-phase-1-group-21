package models.crop;

import models.game.Consumable;
import models.game.Item;
import models.game.ItemType;

public class PlantedSeed {
	private FertilizerType fertilizerType = null; // not implemented yet.
	private int stage = 0;
	private int day = 1;
	private int lastHarvest = -1;
	private int waterStage = 2;
	SeedInfo seedInfo;

	protected PlantedSeed(SeedInfo seedInfo) {
		this.seedInfo = seedInfo;
	}

	public void water() {
		// This method is supposed to be called by its tile.

		waterStage = 2;
	}

	public Item harvest() {
		// This method is supposed to be called by its tile.

		if (lastHarvest == -1) {
			if (stage == seedInfo.getStages().size()) {
				lastHarvest = day;
				if (seedInfo.getEnergy() == null) {
					return new Item(seedInfo.getResultName(), ItemType.SALABLE, seedInfo.getBaseSellPrice(), 1);
				}
				return new Consumable(seedInfo.getResultName(), seedInfo.getEnergy(), seedInfo.getBaseSellPrice());
			}
		}
		else if (day - lastHarvest > seedInfo.getRegrowthTime()) {
			lastHarvest = day;
			if (seedInfo.getEnergy() == null) {
				return new Item(seedInfo.getResultName(), ItemType.SALABLE, seedInfo.getBaseSellPrice(), 1);
			}
			return new Consumable(seedInfo.getResultName(), seedInfo.getBaseSellPrice());
		}
		return null;
	}

	public boolean nextDay() {
		// This method is supposed to be called by its tile.

		waterStage--;
		if (waterStage < 0)
			return false;

		day++;
		stage = seedInfo.getStage(day);
		return true;
	}

	public boolean isOneTime() {
		return seedInfo.isOneTime();
	}
}
