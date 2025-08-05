package handlers;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import com.google.gson.*;

public class LLMHandler {

    private static String loadApiKey() throws IOException {
        String filePath = "API";
        Path currentPath = Paths.get(filePath).toAbsolutePath();
        System.out.println("Looking for API file at: " + currentPath);
        System.out.println("File exists: " + Files.exists(currentPath));

        List<String> lines = Files.readAllLines(currentPath, StandardCharsets.UTF_8);

        if (lines.isEmpty()) {
            throw new IOException("API file is empty");
        }

        return lines.get(0).trim();
    }

    private static final Gson gson = new GsonBuilder().create();
    public static String generateMsg(String playerQuery, String npcInfo, String gameInfo) throws IOException {
        String apiKey = loadApiKey();
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
}