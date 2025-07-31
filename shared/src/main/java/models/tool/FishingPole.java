package models.tool;

import data.FishData;
import models.Item.Item;
import models.game.Game;
import models.game.Player;
import models.map.Coord;
import models.map.Lake;
import models.map.Tile;
import models.map.Weather;
import models.result.Result;
import models.skill.SkillType;
import models.time.Season;

import java.util.ArrayList;
import java.util.Random;

public class FishingPole extends Tool {
	public FishingPole() {
		super(ToolType.FISHING_POLE);
	}

	@Override
	public Result<Item> use(Coord coord, Game game, Player player) {
		Tile tile = player.getMap().getTile(coord);
		if(tile.getPlacable(Lake.class) == null)
			return null;

		Tool fishingPole = (Tool) player.getItemInHand();

		//TODO : check skills
		if(fishingPole.toolMaterialType.equals(ToolMaterialType.EDUCATIONAL) || fishingPole.toolMaterialType.equals(ToolMaterialType.BAMBOO) || fishingPole.toolMaterialType.equals
				(ToolMaterialType.IRIDIUM) || fishingPole.toolMaterialType.equals(ToolMaterialType.FIBERGLASS))
			player.setEnergy(player.getEnergy() + 1);

		double weatherCofficient = game.weatherCoefficient();

		player.setSkillExp(SkillType.FISHING , player.getSkillExp(SkillType.FISHING) + 5);

		if(fishingPole.toolMaterialType.equals(ToolMaterialType.EDUCATIONAL) ||
				fishingPole.toolMaterialType.equals(ToolMaterialType.BAMBOO))
			player.decreaseEnergy((int)(8* weatherCofficient));

		else if(fishingPole.toolMaterialType.equals(ToolMaterialType.FIBERGLASS))
			player.decreaseEnergy((int)(6 * weatherCofficient));

		else if(fishingPole.toolMaterialType.equals(ToolMaterialType.IRIDIUM))
			player.decreaseEnergy((int)(4 * weatherCofficient));

		double amount = Math.random() * (player.getSkillLevel(SkillType.FISHING) + 2);
		if(game.getWeather().equals(Weather.SUNNY))
			amount *= 1.5;
		if(game.getWeather().equals(Weather.RAINY))
			amount *= 1.2;
		if(game.getWeather().equals(Weather.STORM))
			amount *= 0.5;

		System.out.println(amount);

		if(fishingPole.toolMaterialType.equals(ToolMaterialType.EDUCATIONAL))
			return Result.success(Item.build(getCheapestFish(game) , Math.min((int) amount , 6)));

		else if(fishingPole.toolMaterialType.equals(ToolMaterialType.BAMBOO) || fishingPole.toolMaterialType.equals
				(ToolMaterialType.IRIDIUM) || fishingPole.toolMaterialType.equals(ToolMaterialType.FIBERGLASS))
			return Result.success(Item.build(randomFish(game, player) , Math.min((int) amount , 6)));

		return null;
	}

	public static String getCheapestFish(Game game){
		if(game.getSeason() == Season.SPRING)
			return "Herring";
		if(game.getSeason() == Season.SUMMER)
			return "Sunfish";
		if(game.getSeason() == Season.AUTUMN)
			return "Sardine";
		if(game.getSeason() == Season.WINTER)
			return "Perch";
		return null;
	}

	public static String randomFish(Game game, Player player){
		ArrayList<FishData> allFishes = FishData.getFishes();
		ArrayList<String> catchFishes  = new ArrayList<>();
		for(FishData fishData : allFishes){
			if(fishData.getSeason().equalsIgnoreCase(game.getSeason().toString())){
				if(player.getSkillLevel(SkillType.FISHING) >= 4){
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
