package models.game;

import java.util.ArrayList;

public class Gift {
    private final Player Sender, Receiver;
    private Double rate;
    private final Item item;

    public Gift(Player sender, Player receiver, Item item) {
        Sender = sender;
        Receiver = receiver;
        this.item = item;
    }



}
