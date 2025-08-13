package models.crop;

import Asset.SharedAssetManager;
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
import models.sprite.GameSprite;
import models.time.Season;

import java.io.Serializable;

public class PlantedTree implements Placable, Harvestable, Serializable, DailyUpdate {
	private int stage = 0;
	private int day = 1;
	private int lastHarvest = 0;
	private boolean readyToHarvest = false;
	public TreeData treeData;
	String texture;
	private transient GameSprite sprite;

	public PlantedTree() {}

	public PlantedTree(TreeData treeData) {
		this.treeData = treeData;
		if(stage == treeData.getStages().size()){
			if(readyToHarvest) {
				if(treeData.getHarvestTexture() != null) {
					texture = treeData.getHarvestTexture();
				}
			}
			else
				texture = treeData.getSeasonTexture().get(0);
		}
		else
			texture = treeData.getStageTexture(stage);
		sprite = new GameSprite(texture);
	}

	public Item harvest(Player player) {
		// This method is supposed to be called by its tile.
		if (readyToHarvest) {
			player.setSkillExp(SkillType.FORAGING , player.getSkillExp(SkillType.FORAGING) + 5);
			readyToHarvest = false;
			lastHarvest = day;
			sprite.setTexture(SharedAssetManager.getOrLoad(treeData.getSeasonTexture().get(0)));
			return Item.build(treeData.getResultName(), 1);
		}

		return null;
	}

	public boolean nextDay(Game g) {
		// This method is supposed to be called by its tile.

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
		if (readyToHarvest && g != null && treeData.getSeasons().contains(g.getSeason().toString().toLowerCase())) {
			sprite.setTexture(treeData.getHarvestTexture());
		}
		else if (stage == treeData.getStages().size()) {
			if(g == null){
				sprite.setTexture(treeData.getSeasonTexture().get(0));
				return false;
			}
			if(g.getSeason().equals(Season.SPRING))
				sprite.setTexture(treeData.getSeasonTexture().get(0));
			if(g.getSeason().equals(Season.SUMMER))
				sprite.setTexture(treeData.getSeasonTexture().get(1));
			if(g.getSeason().equals(Season.AUTUMN))
				sprite.setTexture(treeData.getSeasonTexture().get(2));
			if(g.getSeason().equals(Season.WINTER))
				sprite.setTexture(treeData.getSeasonTexture().get(3));
		}
		else {
			sprite.setTexture(treeData.getStageTexture(stage));
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

	@Override
	public String getTexture() {
		return texture;
	}

	@Override
	public Sprite spriteGetter() {
		return sprite;
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

	private void resetSprite() {
		if (readyToHarvest) {
			sprite.setTexture(treeData.getHarvestTexture());
		}
		else if (stage == treeData.getStages().size()) {
			sprite.setTexture(treeData.getSeasonTexture().get(0));
		}
		else {
			sprite.setTexture(treeData.getStageTexture(stage));
		}
	}
}
