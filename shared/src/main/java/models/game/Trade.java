package models.game;

import models.Item.Item;

import java.io.Serializable;

public class Trade implements Serializable {
	// Be careful! All ID's have to be distinct
	private int ID;
	private Player sender, receiver;
	private int offerPrice;
	private Item offerItem; // amount is inside the item
	private int requestPrice;
	private Item requestItem;
	private boolean response;
	private boolean responsed = false;
	private TradeType tradeType;

	public Trade() {}

	public Trade(int ID, Player sender, Player receiver, int offerPrice, Item offerItem, int requestPrice, Item requestItem , TradeType tradeType) {
		this.ID = ID;
		this.sender = sender;
		this.receiver = receiver;
		this.offerPrice = offerPrice;
		this.offerItem = offerItem;
		this.requestPrice = requestPrice;
		this.requestItem = requestItem;
		this.tradeType = tradeType;
	}

	public int getID() {
		return ID;
	}

	public Player getSender() {
		return sender;
	}

	public Player getReceiver() {
		return receiver;
	}

	public int getOfferPrice() {
		return offerPrice;
	}

	public Item getOfferItem() {
		return offerItem;
	}

	public int getRequestPrice() {
		return requestPrice;
	}

	public Item getRequestItem() {
		return requestItem;
	}

	public boolean isResponse() {
		return response;
	}

	public boolean isResponsed() {
		return responsed;
	}

	public TradeType getTradeType() {
		return tradeType;
	}

	public void setResponse(boolean response) {
		this.response = response;
	}

	public void setResponsed(boolean responsed) {
		this.responsed = responsed;
	}

	@Override
	public String toString() {
		return String.format(
				"Trade{id=%d, sender=%s, receiver=%s, offerPrice=%d, offerItem=%s, requestPrice=%d, requestItem=%s, tradeType=%s, response=%b, responsed=%b}",
				ID,
				sender.getUser().getUsername(),
				receiver.getUser().getUsername(),
				offerPrice,
				offerItem.getName(),
				requestPrice,
				requestItem.getName(),
				tradeType,
				response,
				responsed
		);
	}
}
