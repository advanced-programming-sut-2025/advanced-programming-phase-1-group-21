package io.github.StardewValley.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.StardewValley.App;
import io.github.StardewValley.network.NetworkDataBaseController;
import models.result.Result;
import models.user.User;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UserUtil {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static final String FILE_PATH = "user.json";

    private static void saveUser(String username, String password) {
        Map<String, String> data = new HashMap<>();
        data.put("username", username);
        data.put("password", password);

        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            gson.toJson(data, writer);
            System.out.println("User saved successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Map<String, String> loadUser() {
        try (FileReader reader = new FileReader(FILE_PATH)) {
            return gson.fromJson(reader, Map.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean load() {
        Map<String, String> user = loadUser();
        if (user == null) return false;
        Result<User> r = NetworkDataBaseController.login(user.get("username"), user.get("password"), false);
        if (r.isError()) return false;
        App.getInstance().logedInUser = r.getData();
        return true;
    }

    public static void save(String username, String password) {
        saveUser(username, password);
    }

    public static void remove() {
        saveUser("", "");
    }
}
