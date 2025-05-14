package models.tool;

import models.App;
import models.Item.Consumable;
import models.Item.Item;
import models.data.FishData;
import models.game.Player;
import models.map.Coord;
import models.map.Lake;
import models.map.Tile;
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
	public Result<Item> use(Coord coord) {
		Player player = App.game.getCurrentPlayer();
		Tile tile = player.getMap().getTile(coord);
		if(tile.getPlacable(Lake.class) == null)
			return null;

		Tool fishingPole = (Tool) App.game.getCurrentPlayer().getItemInHand();

		//TODO : check skills
		if(fishingPole.toolMaterialType.equals(ToolMaterialType.EDUCATIONAL) || fishingPole.toolMaterialType.equals(ToolMaterialType.BAMBOO) || fishingPole.toolMaterialType.equals
				(ToolMaterialType.IRIDIUM) || fishingPole.toolMaterialType.equals(ToolMaterialType.FIBERGLASS))
			player.setEnergy(player.getEnergy() + 1);

		double weatherCofficient = App.game.weatherCofficient();

		if(fishingPole.toolMaterialType.equals(ToolMaterialType.EDUCATIONAL) ||
				fishingPole.toolMaterialType.equals(ToolMaterialType.BAMBOO))
			player.decreaseEnergy((int)(8* weatherCofficient));

		else if(fishingPole.toolMaterialType.equals(ToolMaterialType.FIBERGLASS))
			player.decreaseEnergy((int)(6 * weatherCofficient));

		else if(fishingPole.toolMaterialType.equals(ToolMaterialType.IRIDIUM))
			player.decreaseEnergy((int)(4 * weatherCofficient));

		if(fishingPole.toolMaterialType.equals(ToolMaterialType.EDUCATIONAL))
			return Result.success(Item.build(getCheapestFish() , 1));

		else if(fishingPole.toolMaterialType.equals(ToolMaterialType.BAMBOO) || fishingPole.toolMaterialType.equals
				(ToolMaterialType.IRIDIUM) || fishingPole.toolMaterialType.equals(ToolMaterialType.FIBERGLASS))
			return Result.success(Item.build(randomFish() , 1));

		return null;

	}

	public static String getCheapestFish(){
		if(App.game.getSeason() == Season.SPRING)
			return "Herring";
		if(App.game.getSeason() == Season.SUMMER)
			return "Sunfish";
		if(App.game.getSeason() == Season.AUTUMN)
			return "Sardine";
		if(App.game.getSeason() == Season.WINTER)
			return "Perch";
		return null;
	}

	public static String randomFish(){
		ArrayList<FishData> allFishes = FishData.getFishes();
		ArrayList<String> catchFishes  = new ArrayList<>();
		for(FishData fishData : allFishes){
			if(fishData.getSeason().equals(App.game.getSeason())){
				if(App.game.getCurrentPlayer().getSkillLevel(SkillType.FISHING) >= 4){
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
