package models.map;

import controllers.RequirementChecker;
import models.App;
import models.DailyUpdate;
import models.Item.Item;
import models.data.ShopData;
import models.game.Inventory;
import models.result.Result;
import models.result.errorTypes.GameError;

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
		for (ShopItemInstance item: items) {
			if (isItemAvailable(item)) {
				available.add(item);
			}
		}
		return available;
	}

	public ShopItemInstance getAvailableShopItemInstance(String name) {
		for (ShopItemInstance itemInstance : getAvailableItems()) {
			ShopData data = itemInstance.getData();
			if (data.getName().equalsIgnoreCase(name)) {
				return itemInstance;
			}
		}
		return null;
	}

	/**
	 * this function removes ingredient from Inventory and adds the sell count
	 * you can imagine you have the "name" after calling this function
	 * better be a controller function but i'm goshad!
	 * @param name
	 * @param amount
	 * @return Error or null
	 */
	public Result<Void> prepareBuy(String name, int amount, Inventory inventory) {
		ShopItemInstance itemInstance = getAvailableShopItemInstance(name);
		if (itemInstance == null) return Result.failure(GameError.ITEM_IS_NOT_AVAILABLE);
		ShopData data = itemInstance.getData();

		if (!(new RequirementChecker()).checkShopDate(data)) return Result.failure(GameError.REQUIREMENT_NOT_SATISFIED);
		if (!isItemAvailable(itemInstance)) return Result.failure(GameError.ITEM_IS_NOT_AVAILABLE);

		int remaining = itemInstance.getRemaining();
		if (amount > remaining)
			return Result.failure(GameError.ITEM_IS_NOT_AVAILABLE);

		java.util.Map<String, Integer> baseIngredients = data.getIngredients();
		List<Item> requiredItems = new ArrayList<>();
		for (java.util.Map.Entry<String, Integer> entry : baseIngredients.entrySet()) {
			String ingredientName = entry.getKey();
			int totalRequired = entry.getValue() * amount;

			Item required = Item.build(ingredientName, totalRequired);
			requiredItems.add(required);
		}

		Item coins = Item.build("coin", amount * data.getPrice(App.game.getSeason()));
		requiredItems.add(coins);

		if (!inventory.canRemoveItemList(requiredItems))
			return Result.failure(GameError.NOT_ENOUGH_ITEMS);

		inventory.removeItemList(requiredItems);
		itemInstance.incrementSold(amount);

		return Result.success(null);
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
			return data.getDailyLimit() == -1 || soldToday < data.getDailyLimit();
		}

		@Override
		public boolean nextDay() {
			resetDailyLimit();
			return true;
		}
		@Override
		public String toString() {
			return data.toString() +
					" | Sold Today: " + soldToday;
		}

		public int getRemaining() {
			if (data.getDailyLimit() == -1) return Integer.MAX_VALUE;
			return data.getDailyLimit() - soldToday;
		}
	}
}
