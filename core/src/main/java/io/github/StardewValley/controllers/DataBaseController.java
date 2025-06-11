package io.github.StardewValley.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import io.github.StardewValley.models.Item.Item;
import io.github.StardewValley.models.time.Season;
import io.github.StardewValley.models.user.User;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DataBaseController {
    public static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static ArrayList<User> readAllUsers() {
        return readAllUsers(DataBaseController.gson, "Users.json");
    }

    public static ArrayList<User> readAllUsers(Gson gson , String filePath){
        File file = new File(filePath);
        if (!file.exists() || file.length() == 0) {
            return new ArrayList<>();
        }

        try (FileReader reader = new FileReader(file)) {
            Type listType = new TypeToken<ArrayList<User>>(){}.getType();
            ArrayList<User> existingData = gson.fromJson(reader, listType);
            return existingData != null ? existingData : new ArrayList<>();
        } catch (IOException e) {
            System.err.println("error in reading file" + e.getMessage());
            return new ArrayList<>();
        }
    }

    public static User findUserByUsername(String username) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        ArrayList<User> users = readAllUsers(gson , "Users.json");
        for(User user : users)
            if (user.getUsername().equals(username))
                return user;
        return null;
    }

    public static void editUser(User user) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        ArrayList<User> users = readAllUsers(gson , "Users.json");

        for(int i = 0 ; i < users.size(); i++){
            if(users.get(i).getUsername().equals(user.getUsername())){
                users.set(i,user);
            }
        }

        try (FileWriter writer = new FileWriter("Users.json")) {
            gson.toJson(users, writer);
        } catch (IOException e) {
            System.err.println("error" + e.getMessage());
            throw e;
        }
    }

    private static List<Season> parseSeasons(String seasonStr) {
        List<Season> seasons = new ArrayList<>();
        String[] parts = seasonStr.split(" & ");

        for (String part : parts) {
            switch (part.toUpperCase()) {
                case "SPRING":
                    seasons.add(Season.SPRING);
                    break;
                case "SUMMER":
                    seasons.add(Season.SUMMER);
                    break;
                case "FALL":
                case "AUTUMN":
                    seasons.add(Season.AUTUMN);
                    break;
                case "WINTER":
                    seasons.add(Season.WINTER);
                    break;
            }
        }
        return seasons;
    }

    public static ArrayList<Item> readShopProducts(Gson gson, String filePath) {
        File file = new File(filePath);
        if (!file.exists() || file.length() == 0) {
            return new ArrayList<>();
        }

        try (FileReader reader = new FileReader(file)) {
            Type listType = new TypeToken<ArrayList<Item>>(){}.getType();
            ArrayList<Item> existingData = gson.fromJson(reader, listType);
            return existingData != null ? existingData : new ArrayList<>();
        } catch (IOException e) {
            System.err.println("error in reading file" + e.getMessage());
            return new ArrayList<>();
        }
    }

}
