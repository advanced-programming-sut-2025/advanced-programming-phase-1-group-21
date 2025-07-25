package models.crop;

import com.badlogic.gdx.graphics.Texture;
import data.items.SeedData;
import models.DailyUpdate;
import models.Item.Item;
import models.game.Game;
import models.game.Player;
import models.map.Placable;
import models.map.TileType;
import models.skill.SkillType;

import java.io.Serializable;

public class PlantedSeed implements Placable, Harvestable, DailyUpdate, Serializable {
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

		waterStage = Math.max(2, waterStage);
	}

	public Item harvest(Player player) {
		// This method is supposed to be called by its tile.
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
		System.err.println("WATER: " + waterStage);

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

	public void fertilize(FertilizerType fertilizerType) {
		waterStage += fertilizerType.getDays();
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
	public Texture getTexture() {
		return null;
	}

	public String getResultName() {
		return seedData.getResultName();
	}

	public boolean isHarvestReady() {
		return readyToHarvest;
	}

	@Override
	public String toString() {
		return String.format(
				"PlantedSeed {day=%d, stage=%d, watered=%d, lastHarvest=%s, regrowth=%s}\ndataInfo:%s",
				day,
				stage,
				waterStage,
				lastHarvest == -1 ? "never" : lastHarvest,
				isOneTime() ? "no" : "yes",
				seedData.toString()
		);
	}
}
