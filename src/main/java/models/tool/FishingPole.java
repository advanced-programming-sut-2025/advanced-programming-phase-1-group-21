package models.tool;

import models.App;
import models.Item.Item;
import models.game.Player;
import models.map.Coord;
import models.map.Lake;
import models.map.Tile;
import models.result.Result;
import models.time.Season;

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
		if(fishingPole.toolMaterialType.equals(ToolMaterialType.PRIMITIVE) ||
				fishingPole.toolMaterialType.equals(ToolMaterialType.BAMBOO))
			player.decreaseEnergy(8);

		if(fishingPole.toolMaterialType.equals(ToolMaterialType.FIBERGLASS))
			player.decreaseEnergy(6);

		if(fishingPole.toolMaterialType.equals(ToolMaterialType.IRIDIUM))
			player.decreaseEnergy(4);

		if(fishingPole.toolMaterialType.equals(ToolMaterialType.PRIMITIVE))
			return Result.success(Item.build(getCheapestFish() , 1));

		if(fishingPole.toolMaterialType.equals(ToolMaterialType.BAMBOO) || fishingPole.toolMaterialType.equals
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
		return null;
	}

	@Override
	public ToolMaterialType getToolMaterialType() {
		return toolMaterialType;
	}
}
