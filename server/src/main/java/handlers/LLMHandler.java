package handlers;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import com.google.gson.*;

public class LLMHandler {

    private static final Gson gson = new GsonBuilder().create();

    public static String generateMsg(String playerQuery, String npcInfo, String gameInfo) throws IOException {
        String apiKey = System.getenv("OPENROUTER_API_KEY");
        if (apiKey == null || apiKey.isEmpty()) {
            throw new RuntimeException("OPENROUTER_API_KEY environment variable not set");
        }

        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("model", "deepseek/deepseek-r1-0528-qwen3-8b:free");

        JsonArray messages = new JsonArray();
        JsonObject message = new JsonObject();
        message.addProperty("role", "user");
        message.addProperty("content", String.format(
                "I want you to be like a NPC from the GAME OF STARDEW VALLEY. " +
                        "Response should be exactly one sentence. " +
                        "Here is Game Information: %s. " +
                        "Here is NPC info: %s. " +
                        "Here is What player says to you: %s",
                gameInfo, npcInfo, playerQuery));

        messages.add(message);
        requestBody.add("messages", messages);

        HttpURLConnection connection = (HttpURLConnection) new URL("https://openrouter.ai/api/v1/chat/completions")
                .openConnection();

        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Authorization", "Bearer " + apiKey);
        connection.setDoOutput(true);

        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = gson.toJson(requestBody).getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            try (InputStream is = connection.getInputStream();
                 BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {

                JsonObject jsonResponse = gson.fromJson(reader, JsonObject.class);
                JsonArray choices = jsonResponse.getAsJsonArray("choices");
                if (choices.size() > 0) {
                    JsonObject firstChoice = choices.get(0).getAsJsonObject();
                    JsonObject messageResponse = firstChoice.getAsJsonObject("message");
                    return messageResponse.get("content").getAsString();
                }
                throw new RuntimeException("No choices in API response");
            }
        } else {
            try (InputStream es = connection.getErrorStream();
                 BufferedReader reader = new BufferedReader(new InputStreamReader(es))) {

                JsonObject errorResponse = gson.fromJson(reader, JsonObject.class);
                throw new RuntimeException("API request failed with code " + responseCode +
                        ": " + (errorResponse != null ? errorResponse.toString() : "No error details"));
            }
        }
    }

    public static void main(String[] args) {
        try {
            String gameInfo = "Stardew Valley is a farming simulation game...";
            String npcInfo = "You are Linus, a kind but solitary man who lives in a tent...";
            String playerQuery = "Why do you live outside?";

            String response = generateMsg(playerQuery, npcInfo, gameInfo);
            System.out.println("NPC Response: " + response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}