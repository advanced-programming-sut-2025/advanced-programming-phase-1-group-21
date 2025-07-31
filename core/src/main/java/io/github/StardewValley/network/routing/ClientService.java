package io.github.StardewValley.network.routing;

import Network.Message;
import Network.MessageType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import io.github.StardewValley.App;
import io.github.StardewValley.Main;
import io.github.StardewValley.network.NetworkLobbyController;
import io.github.StardewValley.network.Refreshable;
import io.github.StardewValley.views.menu.CLI.GameTerminalView;
import io.github.StardewValley.views.menu.GUI.LobbyScreen;
import models.game.Game;
import models.game.Player;
import models.map.Map;
import models.map.MapBuilder;
import models.network.Chat;
import models.network.Lobby;
import models.network.LobbyUser;
import models.result.Result;
import models.result.errorTypes.AuthError;
import models.user.User;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

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
        Result<Lobby> result = NetworkLobbyController.getLobby();
        Lobby lobby = result.getData();

        /*
        SHOULD CHECK IF USER IS ALREADY IN A GAME...
         */
        ArrayList<Player> players = new ArrayList<>();
        MapBuilder mapBuilder = new MapBuilder();

        Gdx.app.postRunnable(() -> {

            for (LobbyUser lobbyUser : lobby.getUsers()) {
                User user = lobbyUser.user;
                int seed = lobbyUser.mapID;
                Map map = mapBuilder.buildFarm(new Random(seed));
                Player player = new Player(user, map);
                players.add(player);
            }

            Game game = new Game(players);
            App.getInstance().initGame(game);
        });
    }
}
