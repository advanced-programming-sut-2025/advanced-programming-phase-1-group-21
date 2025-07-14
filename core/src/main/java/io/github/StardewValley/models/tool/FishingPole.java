package io.github.StardewValley.models.tool;

import io.github.StardewValley.App;
import io.github.StardewValley.models.Item.Item;
import io.github.StardewValley.data.FishData;
import io.github.StardewValley.models.game.Player;
import io.github.StardewValley.models.map.Coord;
import io.github.StardewValley.models.map.Lake;
import io.github.StardewValley.models.map.Tile;
import io.github.StardewValley.models.map.Weather;
import io.github.StardewValley.models.result.Result;
import io.github.StardewValley.models.skill.SkillType;
import io.github.StardewValley.models.time.Season;

import java.util.ArrayList;
import java.util.Random;

public class FishingPole extends Tool {
	public FishingPole() {
		super(ToolType.FISHING_POLE);
	}

	@Override
	public Result<Item> use(Coord coord) {
		Player player = App.getInstance().game.getCurrentPlayer();
		Tile tile = player.getMap().getTile(coord);
		if(tile.getPlacable(Lake.class) == null)
			return null;

		Tool fishingPole = (Tool) App.getInstance().game.getCurrentPlayer().getItemInHand();

		//TODO : check skills
		if(fishingPole.toolMaterialType.equals(ToolMaterialType.EDUCATIONAL) || fishingPole.toolMaterialType.equals(ToolMaterialType.BAMBOO) || fishingPole.toolMaterialType.equals
				(ToolMaterialType.IRIDIUM) || fishingPole.toolMaterialType.equals(ToolMaterialType.FIBERGLASS))
			player.setEnergy(player.getEnergy() + 1);

		double weatherCofficient = App.getInstance().game.weatherCoefficient();

		player.setSkillExp(SkillType.FISHING , player.getSkillExp(SkillType.FISHING) + 5);

		if(fishingPole.toolMaterialType.equals(ToolMaterialType.EDUCATIONAL) ||
				fishingPole.toolMaterialType.equals(ToolMaterialType.BAMBOO))
			player.decreaseEnergy((int)(8* weatherCofficient));

		else if(fishingPole.toolMaterialType.equals(ToolMaterialType.FIBERGLASS))
			player.decreaseEnergy((int)(6 * weatherCofficient));

		else if(fishingPole.toolMaterialType.equals(ToolMaterialType.IRIDIUM))
			player.decreaseEnergy((int)(4 * weatherCofficient));

		double amount = Math.random() * (App.getInstance().game.getCurrentPlayer().getSkillLevel(SkillType.FISHING) + 2);
		if(App.getInstance().game.getWeather().equals(Weather.SUNNY))
			amount *= 1.5;
		if(App.getInstance().game.getWeather().equals(Weather.RAINY))
			amount *= 1.2;
		if(App.getInstance().game.getWeather().equals(Weather.STORM))
			amount *= 0.5;

		System.out.println(amount);

		if(fishingPole.toolMaterialType.equals(ToolMaterialType.EDUCATIONAL))
			return Result.success(Item.build(getCheapestFish() , Math.min((int) amount , 6)));

		else if(fishingPole.toolMaterialType.equals(ToolMaterialType.BAMBOO) || fishingPole.toolMaterialType.equals
				(ToolMaterialType.IRIDIUM) || fishingPole.toolMaterialType.equals(ToolMaterialType.FIBERGLASS))
			return Result.success(Item.build(randomFish() , Math.min((int) amount , 6)));

		return null;
	}

	public static String getCheapestFish(){
		if(App.getInstance().game.getSeason() == Season.SPRING)
			return "Herring";
		if(App.getInstance().game.getSeason() == Season.SUMMER)
			return "Sunfish";
		if(App.getInstance().game.getSeason() == Season.AUTUMN)
			return "Sardine";
		if(App.getInstance().game.getSeason() == Season.WINTER)
			return "Perch";
		return null;
	}

	public static String randomFish(){
		ArrayList<FishData> allFishes = FishData.getFishes();
		ArrayList<String> catchFishes  = new ArrayList<>();
		for(FishData fishData : allFishes){
			if(fishData.getSeason().equalsIgnoreCase(App.getInstance().game.getSeason().toString())){
				if(App.getInstance().game.getCurrentPlayer().getSkillLevel(SkillType.FISHING) >= 4){
					catchFishes.add(fishData.getName());
				}
				else{
					if(fishData.getType().equalsIgnoreCase("normal"))
						catchFishes.add(fishData.getName());
				}
			}
		}
		Random rand = new Random();
		return catchFishes.get(rand.nextInt(catchFishes.size()));
	}

	@Override
	public ToolMaterialType getToolMaterialType() {
		return toolMaterialType;
	}

}
