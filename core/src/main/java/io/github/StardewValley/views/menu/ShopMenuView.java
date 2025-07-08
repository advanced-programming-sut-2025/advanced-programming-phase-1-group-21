package io.github.StardewValley.views.menu;

public class ShopMenuView implements Menu {
    private static ShopMenuView instance;

    private ShopMenuView() {
    }

    public static ShopMenuView getInstance() {
        if (instance == null)
            instance = new ShopMenuView();
        return instance;
    }

    public void Result(String command) {

    }
}
