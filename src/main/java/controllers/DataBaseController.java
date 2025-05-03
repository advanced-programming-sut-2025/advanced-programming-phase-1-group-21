package controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import models.game.Item;
import models.result.Result;
import models.time.Season;
import models.user.User;
import models.crop.*;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DataBaseController {
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
        ArrayList<User> users = new ArrayList<>();
        users = readAllUsers(gson , "Users.json");

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

    public static List<SeedInfo> loadCropsFromCSV(String filePath) {
        List<SeedInfo> crops = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            // Skip header line
            br.readLine();

            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");

                String name = values[0];
                String source = values[1];

                List<Integer> stages = Arrays.stream(values[2].split("-"))
                        .map(Integer::parseInt)
                        .collect(Collectors.toList());

                int totalHarvestTime = Integer.parseInt(values[3]);
                boolean oneTime = Boolean.parseBoolean(values[4]);

                Integer regrowthTime = values[5].equals("-") ? null : Integer.parseInt(values[5]);

                int baseSellPrice = Integer.parseInt(values[6]);
                boolean isEdible = Boolean.parseBoolean(values[7]);

                Integer energy = values[8].equals("-") ? null : Integer.parseInt(values[8]);
                Integer baseHealth = values[9].equals("-") ? null : Integer.parseInt(values[9]);

                List<Season> seasons = parseSeasons(values[10]);

                boolean canBecomeGiant = Boolean.parseBoolean(values[11]);

                crops.add(new SeedInfo(name, source, stages, totalHarvestTime, oneTime, regrowthTime,
                        baseSellPrice, isEdible, energy, baseHealth, seasons, canBecomeGiant));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return crops;
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
