package models.map;

import com.google.gson.Gson;
import controllers.DataBaseController;
import models.Item.Item;
import models.data.shop.ShopData;

import java.util.ArrayList;
import java.util.List;

public class Shop extends Building {
	private List<ShopData> items;
	private final TileType shopType;

	public Shop(TileType shopType) {
		if (!shopType.isShop())
			throw new IllegalArgumentException("shop type must be a shop");

		this.shopType = shopType;
		this.map = (new MapBuilder()).buildShop();
		items = ShopData.getRecipeByShop(getShopName());
	}

	public String getShopName() {
		String name;
		switch (shopType) {
			case BLACKSMITH:
				name = "Blacksmith";
				break;
			case JOJAMART:
				name = "JojaMart";
				break;
			case PIERR_STORE:
				name = "Pierre's General Store";
				break;
			case CARPENTER_SHOP:
				name = "Carpenter's Shop";
				break;
			case FISH_SHOP:
				name = "Fish Shop";
				break;
			case MARINE_SHOP:
				name = "Marnie's Ranch";
				break;
			case STARDROP_SALOON:
				name = "The Stardrop Saloon";
				break;
			default:
				throw new IllegalArgumentException("Unknown shop type: " + shopType);
		}
		return name;
	}


	public ArrayList<String> showProducts(){
		ArrayList<String > output = new ArrayList<>();
		output.add(shopType.toString() + " :");

		//TODO
		return output;
	}

	public TileType getShopType() {
		return shopType;
	}

	@Override
	public boolean canEnter() {
		return true;
	}

	@Override
	public String getSprite() {
		return "" + shopType.getSymbol();
	}
}
