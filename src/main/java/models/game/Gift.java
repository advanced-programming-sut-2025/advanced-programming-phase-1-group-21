package models.game;

import models.Item.Item;

import java.io.Serializable;

public class Gift implements Serializable {
    private final Player Sender, Receiver;
    private double rate;
    private final Item item;
    private int id;

    public Gift(Player sender, Player receiver, Item item , int id) {
        Sender = sender;
        Receiver = receiver;
        this.item = item;
        this.rate = 0;
        this.id = id;
    }

    public Player getSender() {
        return Sender;
    }

    public int getId() {
        return id;
    }

    public Player getReceiver() {
        return Receiver;
    }

    public Double getRate() {
        return rate;
    }

    public Item getItem() {
        return item;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return String.format(
                "Gift{id=%d, sender=%s, receiver=%s, item=%s, rate=%.2f}",
                id,
                Sender.getUser().getUsername(),
                Receiver.getUser().getUsername(),
                item.getName(),
                rate
        );
    }
}
