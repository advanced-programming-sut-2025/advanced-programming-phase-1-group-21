package models.crop;

import models.App;
import models.DailyUpdate;
import models.Item.Consumable;
import models.Item.Item;
import models.Item.ItemType;
import models.data.items.SeedData;
import models.game.Game;
import models.game.Player;
import models.map.Placable;
import models.map.TileType;
import models.skill.SkillType;

public class PlantedSeed implements Placable, Harvestable, DailyUpdate {
	private FertilizerType fertilizerType = null; // not implemented yet.
	private int stage = 0;
	private int day = 1;
	private int lastHarvest = 0;
	private int waterStage = 2;
	private boolean readyToHarvest = false;
	private final SeedData seedData;

	public PlantedSeed(SeedData seedData) {
		this.seedData = seedData;
	}

	public void water() {
		// This method is supposed to be called by its tile.

		waterStage = 2;
	}

	public Item harvest() {
		// This method is supposed to be called by its tile.
		Player player = App.game.getCurrentPlayer();
		if (readyToHarvest) {
			player.setSkillExp(SkillType.FARMING , player.getSkillExp(SkillType.FARMING) + 5);
			readyToHarvest = false;
			lastHarvest = day;
			return Item.build(seedData.getResultName(), 1);
		}
		return null;
	}

	@Override
	public boolean nextDay(Game g) {
		// This method is supposed to be called by its tile.

		waterStage--;
		if (waterStage < 0)
			return true;

		day++;
		stage = seedData.getStage(day);

		if (!readyToHarvest) {
			if (lastHarvest == 0) {
				if (stage == seedData.getStages().size())
					readyToHarvest = true;
			}
			else if (day - lastHarvest > seedData.getRegrowthTime()) {
				readyToHarvest = true;
			}
		}

		return false;
	}

	public SeedData getData() {
		return seedData;
	}

	public boolean isOneTime() {
		return seedData.isOneTime();
	}

	@Override
	public TileType getTileType() {
		return TileType.PLANTED_SEED;
	}

	@Override
	public boolean isWalkable() {
		return false;
	}

	@Override
	public String getSprite() {
		return "S";
	}

	@Override
	public String toString() {
		return String.format(
				"PlantedSeed {day=%d, stage=%d, watered=%s, lastHarvest=%s, regrowth=%s}\ndataInfo:%s",
				day,
				stage,
				waterStage > 0 ? "yes" : "no",
				lastHarvest == -1 ? "never" : lastHarvest,
				isOneTime() ? "no" : "yes",
				seedData.toString()
		);
	}
}