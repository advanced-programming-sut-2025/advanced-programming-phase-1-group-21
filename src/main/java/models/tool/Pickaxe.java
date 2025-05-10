package models.tool;

import models.App;
import models.Item.Item;
import models.game.Player;
import models.map.*;
import models.result.Result;
import models.result.errorTypes.GameError;

import java.util.Map;
import java.util.Set;

public class Pickaxe extends Tool {
	public static final Set<TileType> HARVESTABLE_TILE_TYPES = Set.of(
			TileType.SIMPLE_ROCK,
			TileType.COPPER_ROCK,
			TileType.STEEL_ROCK,
			TileType.GOLD_ROCK,
			TileType.IRIDIUM_ROCK
	);

	public static final Map<ToolMaterialType, Set<TileType>> FORAGING_BY_TOOL = Map.of(
			ToolMaterialType.STEEL, Set.of(
					TileType.SIMPLE_ROCK,
					TileType.COPPER_ROCK,
					TileType.STEEL_ROCK,
					TileType.GOLD_ROCK
			),
			ToolMaterialType.COPPER, Set.of(
					TileType.SIMPLE_ROCK,
					TileType.COPPER_ROCK,
					TileType.STEEL_ROCK
			),
			ToolMaterialType.PRIMITIVE, Set.of(
					TileType.SIMPLE_ROCK,
					TileType.COPPER_ROCK
			)
	);

	public Pickaxe() {
		super(ToolType.PICKAXE);
	}

	@Override
	public Result<Item> use(Coord coord) {
		Player player = App.game.getCurrentPlayer();
		MapType mapType = player.getMap().mapType;

		if (mapType != MapType.FARM && mapType != MapType.MINES)
			return Result.failure(GameError.YOU_CANT_USE_PICKAXE_HERE);

		Tile tile = player.getMap().getTile(coord);
		TileType tileType = tile.getTileType();
		int use = 0;

		if (tileType == TileType.PLOWED) {
			tile.setTileType(TileType.UNPLOWED);
			use = 1;
		}
		else if (tileType.isForaging()) {
			if (toolMaterialType == ToolMaterialType.GOLD || toolMaterialType == ToolMaterialType.IRIDIUM) {
				if (HARVESTABLE_TILE_TYPES.contains(tileType)) {
					tile.setTileType(TileType.UNPLOWED);
					use = 2;
				}
			} else {
				Set<TileType> allowedTypes = FORAGING_BY_TOOL.get(toolMaterialType);
				if (allowedTypes != null && allowedTypes.contains(tileType)) {
					tile.setPlacable(null);
					tile.setTileType(TileType.UNPLOWED);
					use = 2;
				}
			}
		}

		int energyCost = switch (toolMaterialType) {
			case PRIMITIVE -> 5;
			case COPPER -> 4;
			case STEEL -> 3;
			case GOLD -> 2;
			case IRIDIUM -> 1;
			default -> 0;
        };
		player.decreaseEnergy(use != 0 ? energyCost : 1);

		if (use == 0)
			return Result.success(null, "kolang hich kari nakard");
		if (use == 1)
			return Result.success(null, "zamin gheir shokhmi shod");
		return Result.success(null, "the Rock removed from the ground");
	}

	@Override
	public ToolMaterialType getToolMaterialType() {
		return toolMaterialType;
	}
}
