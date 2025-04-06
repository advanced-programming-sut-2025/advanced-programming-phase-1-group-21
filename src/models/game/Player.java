package models.game;

import models.map.Map;
import models.user.User;

public class Player {
    private User user;
    private Map map;
    private Game game;
    private Energy energy;
    private Inventory inventory;
    private int coins;


    public void addCoins(int coins) {
        this.coins += coins;
    }

    public int getCoins() {
        return coins;
    }
}
