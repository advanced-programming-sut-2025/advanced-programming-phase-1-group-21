package models.map;

import com.google.gson.Gson;
import controllers.DataBaseController;
import models.App;
import models.DailyUpdate;
import models.Item.Item;
import models.data.shop.ShopData;

import java.util.ArrayList;
import java.util.List;

public class Shop extends Building {
	private List<ShopItemInstance> items = new ArrayList<>();
	private final TileType shopType;

	public Shop(TileType shopType) {
		if (!shopType.isShop())
			throw new IllegalArgumentException("shop type must be a shop");

		this.shopType = shopType;
		this.map = (new MapBuilder()).buildShop();

		List<ShopData> list = ShopData.getRecipeByShop(getShopName());
		for (ShopData shopData : list) {
			items.add(new ShopItemInstance(shopData));
		}
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


	private String showProducts(List<ShopItemInstance> items){
		StringBuilder products = new StringBuilder();
		for (ShopItemInstance item : items) {
			products.append(item.toString() + "\n");
		}
		return products.toString();
	}

	public String showProducts(){
		return showProducts(items);
	}

	public boolean isItemAvailable(ShopItemInstance item){
		if (!item.canSellMore()) return false;
		ShopData shopData = item.getData();
		if (shopData.getPrice(App.game.getSeason()) == -1) return false;
		return true;
	}

	public List<ShopItemInstance> getAvailableItems() {
		List<ShopItemInstance> available = new ArrayList<>();
		for (ShopItemInstance item : available) {
			if (isItemAvailable(item)) available.add(item);
		}
		return available;
	}

	public String showAvailableItems() {
		return showProducts(getAvailableItems());
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

	private static class ShopItemInstance implements DailyUpdate {
		private final ShopData data;
		private int soldToday;

		public ShopItemInstance(ShopData data) {
			this.data = data;
			this.soldToday = 0;
		}

		public ShopData getData() {
			return data;
		}

		public int getSoldToday() {
			return soldToday;
		}

		public void incrementSold(int amount) {
			this.soldToday += amount;
		}

		public void resetDailyLimit() {
			this.soldToday = 0;
		}

		public boolean canSellMore() {
			return soldToday < data.getDailyLimit();
		}

		@Override
		public boolean nextDay() {
			resetDailyLimit();
			return true;
		}
		@Override
		public String toString() {
			return data.toString() +
					" | Sold Today: " + soldToday + "/" + data.getDailyLimit();
		}

	}

}
