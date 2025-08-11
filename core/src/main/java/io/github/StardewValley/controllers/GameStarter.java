package io.github.StardewValley.controllers;

import com.badlogic.gdx.Gdx;
import io.github.StardewValley.App;
import models.game.Game;
import models.game.Player;
import models.map.Map;
import models.map.MapBuilder;
import models.network.Lobby;
import models.network.LobbyUser;
import models.user.User;

import java.util.ArrayList;
import java.util.Random;

import static controllers.GameSaver.reloadAllSprites;

public class GameStarter {
    public static void startNewGame(Lobby lobby) {
        ArrayList<Player> players = new ArrayList<>();
        MapBuilder mapBuilder = new MapBuilder();

        Gdx.app.postRunnable(() -> {

            for (LobbyUser lobbyUser : lobby.getUsers()) {
                User user = lobbyUser.user;
                int seed = lobbyUser.mapID;
                Map map = mapBuilder.buildFarm(new Random(seed));
                Player player = new Player(user, map);
                player.setMapID(lobbyUser.mapID);
                players.add(player);
            }

            Game game = new Game(players);
            App.getInstance().initGame(game);
        });
    }

    public static void loadGame(Lobby lobby) {
        Game game = lobby.getGame();
        assert game != null;
        reloadAllSprites(game, game.spriteMap);
        App.getInstance().initGame(game);
    }
}
