package models.map;

import com.google.gson.Gson;
import controllers.DataBaseController;
import models.Item.Item;

import java.util.ArrayList;

public class Shop extends Building {
	private ArrayList<Item> items = new ArrayList<>();
	private final TileType shopType;

	public Shop(TileType shopType) {
		if (!shopType.isShop())
			throw new IllegalArgumentException("shop type must be a shop");

		this.shopType = shopType;
		this.map = (new MapBuilder()).buildShop();
	}

	public ArrayList<String> showProducts(){
		ArrayList<String > output = new ArrayList<>();
		output.add(shopType.toString() + " :");

		for(Item item : items){
			output.add("Item: " + item.getName());
			output.add("price: " + item.getPrice());
			output.add("amount: " + item.getAmount());
			output.add("---------------");
		}
		return output;
	}

	public TileType getShopType() {
		return shopType;
	}

	@Override
	public boolean canEnter() {
		return true;
	}
}
