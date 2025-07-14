package io.github.StardewValley.models.crop;

import io.github.StardewValley.App;
import io.github.StardewValley.models.DailyUpdate;
import io.github.StardewValley.models.Item.Item;
import io.github.StardewValley.data.items.TreeData;
import io.github.StardewValley.models.game.Game;
import io.github.StardewValley.models.game.Player;
import io.github.StardewValley.models.map.Placable;
import io.github.StardewValley.models.map.TileType;
import io.github.StardewValley.models.skill.SkillType;

import java.io.Serializable;

public class PlantedTree implements Placable, Harvestable, Serializable, DailyUpdate {
	private int stage = 0;
	private int day = 1;
	private int lastHarvest = 0;
	private int waterStage = 2;
	private boolean readyToHarvest = false;
	public final TreeData treeData;

	public PlantedTree(TreeData treeData) {
		this.treeData = treeData;
	}

	private void water() {
		// We currently don't need this method.
		// But I don't delete it because there is a small change that we are going to need it later.
		// I just changed it to 'private'.

		waterStage = 2;
	}

	public Item harvest() {
		// This method is supposed to be called by its tile.
		Player player = App.getInstance().game.getCurrentPlayer();
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
