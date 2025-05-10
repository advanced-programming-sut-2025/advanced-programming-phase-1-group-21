package models.game;

import models.Item.Item;

public class Trade {
	// Be careful! All ID's have to be distinct
	private int ID;
	private Player sender, receiver;
	private Item offerItem; // amount is inside the item
	private int requestPrice;
	private Item requestItem;
}
