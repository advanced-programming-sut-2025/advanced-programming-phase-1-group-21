package models.map;

import com.google.gson.Gson;
import controllers.DataBaseController;
import models.game.Item;
import models.result.Result;

import java.util.ArrayList;

public class Shop extends Building {
	private ArrayList<Item> items;
	private ShopType shopType;


	public Shop(ShopType shopType) {
		this.shopType = shopType;
		Gson gson = new Gson();
		if(shopType.equals(ShopType.BLACKSMITH))
			items = DataBaseController.readShopProducts(gson , "data/BlackSmith.json");
	}

	public ArrayList<String> showProducts(){
		ArrayList<String > output = new ArrayList<>();
		output.add(shopType.toString() + " :");

		for(Item item : items){
			output.add("Item: " + item.getName());
			output.add("price: " + item.getCost());
			output.add("amount: " + item.getAmount());
			output.add("---------------");
		}

		return output;
	}
}
