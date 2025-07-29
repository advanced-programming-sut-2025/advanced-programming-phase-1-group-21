package io.github.StardewValley.network.routing;

import Network.Message;
import Network.MessageType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import io.github.StardewValley.Main;
import io.github.StardewValley.network.Refreshable;
import io.github.StardewValley.views.menu.GUI.LobbyScreen;
import models.network.Chat;

/**
 * All functions should be static
 * Because ClientServiceRouter Assumes so
 * we send respond but server actually ignores respond (for now)
 *
 *
 * Imporant:
 *     You can assume that server actually runs this functions!
 *     so if you want a part of function that server needs to hit
 *     put them here!
 *
 *     for example when Game is about to start, server should POKE all clients about starting Game
 *     1. requesting each time from server and asking (HEY, is game about to start?) is bad.
 *     2. So When Game is ready, server actually runs "public static void gameStart()" here, and
 *     you should do what ever you need to do on client (call functions from gameStart()..., Init the Game View, ...)
 */
public class ClientService {

    /**
     * This is actually for testing
     */
    public static void ping() {
        System.out.println("Hey we have been PONGED!");
    }

    public static void handleChat(Chat chat) {

    }

    public static void refresh() {
        Screen screen = Main.getInstance().getScreen();
        if (screen instanceof Refreshable) {
            ((Refreshable) screen).refresh();
        }
    }

    public static void sendMapID() {
        Screen screen = Main.getInstance().getScreen();
        if (screen instanceof LobbyScreen) {
            ((LobbyScreen) screen).sendMap();
        }
    }

    public static void startGame() {
        System.out.println("HERE");
    }
}
