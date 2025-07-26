package models.crop;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import data.items.TreeData;
import models.DailyUpdate;
import models.Item.Item;
import models.game.Game;
import models.game.Player;
import models.map.Placable;
import models.map.TileType;
import models.skill.SkillType;

import java.io.Serializable;

public class PlantedTree implements Placable, Harvestable, Serializable, DailyUpdate {
	private int stage = 0;
	private int day = 1;
	private int lastHarvest = 0;
	private int waterStage = 2;
	private boolean readyToHarvest = false;
	public final TreeData treeData;
	private Sprite sprite;

	public PlantedTree(TreeData treeData) {
		this.treeData = treeData;
		sprite = new Sprite(getTexture());
	}

	private void water() {
		// We currently don't need this method.
		// But I don't delete it because there is a small change that we are going to need it later.
		// I just changed it to 'private'.

		waterStage = 2;
	}

	public Item harvest(Player player) {
		// This method is supposed to be called by its tile.
		if (readyToHarvest) {
			player.setSkillExp(SkillType.FORAGING , player.getSkillExp(SkillType.FORAGING) + 5);
			readyToHarvest = false;
			lastHarvest = day;
			return Item.build(treeData.getResultName(), 1);
		}

		return null;
	}

	public boolean nextDay(Game g) {
		// This method is supposed to be called by its tile.

		/*
		waterStage--;
		if (waterStage < 0)
			return true;
		 */

		day++;
		stage = treeData.getStage(day);

		if (!readyToHarvest) {
			if (lastHarvest == 0) {
				if (stage == treeData.getStages().size())
					readyToHarvest = true;
			}
			else if (day - lastHarvest > treeData.getHarvestCycle()) {
				readyToHarvest = true;
			}
		}
		sprite.setTexture(getTexture());
		return false;
	}
	public void setDay(int day) {
		while (this.day < day)
			nextDay(null);
	}

	public TreeData getData() {
		return treeData;
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
		return "T";
	}

	@Override
	public Texture getTexture() {
		if(stage == treeData.getStages().size()){
			if(readyToHarvest) {
				if(treeData.getHarvestTexture() != null) {
					return new Texture(treeData.getHarvestTexture());
				}
			}
			else
				return new Texture(treeData.getSeasonTexture());//TODO this function logic should depends on current season
		}
		else
			return new Texture(treeData.getStageTexture(stage));
		return null;
	}

	public String getResultName() {
		return treeData.getResultName();
	}

	public boolean isHarvestReady() {
		return readyToHarvest;
	}

	@Override
	public String toString() {
		return String.format(
				"PlantedTree:\nSapling data:\n%s\nday=%d, stage=%d, lastHarvest=%s, harvestCycle=%d",
				treeData.toString(),
				day,
				stage,
				lastHarvest == -1 ? "never" : lastHarvest,
				treeData.getHarvestCycle()
		);
	}
}
