package models.crop;

import Asset.SharedAssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import data.items.SeedData;
import models.DailyUpdate;
import models.Item.Item;
import models.game.Game;
import models.game.Player;
import models.map.Placable;
import models.map.TileType;
import models.skill.SkillType;
import models.sprite.GameSprite;

import java.io.Serializable;

public class PlantedSeed implements Placable, Harvestable, DailyUpdate, Serializable {
	private transient GameSprite sprite;
	private int stage = 0;
	private int day = 1;
	private int lastHarvest = 0;
	private int waterStage = 2;
	private boolean readyToHarvest = false;
	private SeedData seedData;

	public PlantedSeed() {}

	public PlantedSeed(SeedData seedData) {
		this.seedData = seedData;
		sprite = new GameSprite(seedData.getStageTexture(stage));
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
			if (seedData.getRegrowthTexture() != null)
				sprite.setTexture(SharedAssetManager.getOrLoad(seedData.getRegrowthTexture()));
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
		//

		if (!readyToHarvest) {
			if (lastHarvest == 0) {
				if (stage == seedData.getStages().size()) {
					readyToHarvest = true;
				}
			}
			else if (day - lastHarvest > seedData.getRegrowthTime()) {
				readyToHarvest = true;
			}
		}

		if (readyToHarvest) {
			sprite.setTexture(SharedAssetManager.getOrLoad(seedData.getHarvestTexture()));
		}
		else if (stage == seedData.getStages().size()) {
			if (seedData.getRegrowthTexture() != null)
				sprite.setTexture(SharedAssetManager.getOrLoad(seedData.getRegrowthTexture()));
		}
		else {
			sprite.setTexture(SharedAssetManager.getOrLoad(seedData.getStageTexture(stage)));
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
	public String getTexture() {
		return null;
	}

	@Override
	public Sprite spriteGetter() {
		return sprite;
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
