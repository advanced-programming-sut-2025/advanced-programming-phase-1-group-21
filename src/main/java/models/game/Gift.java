package models.game;

public class Gift {
    private final Player Sender, Receiver;
    private Double rate;
    private final Item item;

    public Gift(Player sender, Player receiver, Item item) {
        Sender = sender;
        Receiver = receiver;
        this.item = item;
    }

    public Player getSender() {
        return Sender;
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
}
