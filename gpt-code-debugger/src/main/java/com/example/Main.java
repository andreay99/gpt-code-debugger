package com.example;

import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Main {

    // Main method for CLI-based debugging
    public static void main(String[] args) {
        System.out.println("This is the CLI version. Please run MainGUI for the GUI.");
    }

    // Method used by MainGUI for debugging code snippets
    public static String debugCodeSnippet(String codeSnippet) {
        try {
            return getDebugSuggestions(codeSnippet);
        } catch (IOException e) {
            return "Error: " + e.getMessage();
        }
    }

    // Method to call OpenAI API and get debugging suggestions
    public static String getDebugSuggestions(String code) throws IOException {
        String apiKey = System.getenv("OPENAI_API_KEY");
        if (apiKey == null || apiKey.isEmpty()) {
            throw new IOException("Error: OPENAI_API_KEY environment variable is not set.");
        }

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        String apiUrl = "https://api.openai.com/v1/chat/completions";

        // Constructing the JSON request body safely
        JSONObject messageSystem = new JSONObject();
        messageSystem.put("role", "system");
        messageSystem.put("content", "You are a helpful assistant.");

        JSONObject messageUser = new JSONObject();
        messageUser.put("role", "user");
        messageUser.put("content", "Debug this code:\n" + code);

        JSONArray messages = new JSONArray();
        messages.put(messageSystem);
        messages.put(messageUser);

        JSONObject requestBodyJson = new JSONObject();
        requestBodyJson.put("model", "gpt-3.5-turbo");
        requestBodyJson.put("messages", messages);
        requestBodyJson.put("max_tokens", 150);

        Request request = new Request.Builder()
                .url(apiUrl)
                .post(RequestBody.create(requestBodyJson.toString(), MediaType.parse("application/json")))
                .addHeader("Authorization", "Bearer " + apiKey)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                String responseBody = response.body().string();
                JSONObject jsonResponse = new JSONObject(responseBody);

                if (jsonResponse.has("choices") && !jsonResponse.getJSONArray("choices").isEmpty()) {
                    return jsonResponse
                            .getJSONArray("choices")
                            .getJSONObject(0)
                            .getJSONObject("message")
                            .getString("content");
                } else {
                    throw new IOException("Unexpected response format from OpenAI API.");
                }
            } else {
                int statusCode = response.code();
                if (statusCode == 401) {
                    throw new IOException("Error: Invalid API key or unauthorized access.");
                } else if (statusCode == 429) {
                    throw new IOException("Error: Rate limit exceeded. Please try again later.");
                } else {
                    throw new IOException("Error: Unexpected response from OpenAI API (Code: " + statusCode + ")");
                }
            }
        } catch (IOException e) {
            throw new IOException("Network error or API connection issue: " + e.getMessage(), e);
        }
    }
}