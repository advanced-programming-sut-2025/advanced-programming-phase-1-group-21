package io.github.StardewValley.controllers;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import io.github.StardewValley.App;
import io.github.StardewValley.Main;
import io.github.StardewValley.network.NetworkChatController;
import io.github.StardewValley.views.menu.GUI.ChatScreen;
import io.github.StardewValley.views.menu.GUI.GameScreen;
import models.network.Chat;
import models.network.ChatType;

public class ChatController {

    public final String username;

    public ChatController(String username) {
        this.username = username;
    }


    public void sendMessage(String text) {
        Chat chat = Chat.parseChatMessage(username, text);
        NetworkChatController.sendMessage(chat);
    }

    public void sendMessage(String text , String receiver){
        Chat chat =  new Chat(ChatType.TO_USER, text, username, receiver);
        NetworkChatController.sendMessage(chat);
    }


    public static void onChat(Chat chat) {
        Screen screen = Main.getInstance().getScreen();
        if (screen instanceof GameScreen) {
            GameScreen gameScreen = (GameScreen) screen;
            gameScreen.sendMessageInChat(chat.getSender() + " be " + chat.getType().getUI(), chat.getMessage(), chat.determineColor(App.getInstance().logedInUser.getUsername()));
            if (chat.getType() == ChatType.TO_USER && chat.getReceiver().equals(App.getInstance().logedInUser.getUsername())) {
                gameScreen.showNotification("Seen bezan");
            }
        }
        else {
            System.out.println("WHY ARE YOU SENDING MESSAGE WIHTOUT GAME_SCREEN?");
        }
    }
}
