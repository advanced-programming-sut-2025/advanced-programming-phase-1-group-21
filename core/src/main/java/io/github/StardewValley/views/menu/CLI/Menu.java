package io.github.StardewValley.views.menu.CLI;

import java.io.Serializable;

public interface Menu extends Serializable {
//    Game,
//    LoginMenu,
//    RegisterMenu,
//    MainMenu,
//    TradeMenu,
//    ProfileMenu;
    void Result(String command);

    String ResultText(String command);
}
