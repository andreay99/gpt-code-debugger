package com.example;

import okhttp3.*;
import java.io.IOException;
import org.json.JSONObject;
import org.json.JSONArray;

public class DebuggerUtility {
    private static final String API_KEY = System.getenv("OPENAI_API_KEY");
    private static final String OPENAI_API_URL = "https://api.openai.com/v1/chat/completions";

    public static String debugCodeSnippet(String codeSnippet) {
        if (API_KEY == null || API_KEY.isEmpty()) {
            return "Error: API key is missing. Please set the OPENAI_API_KEY environment variable.";
        }

        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("model", "gpt-3.5-turbo");
            JSONArray messages = new JSONArray();
            JSONObject message = new JSONObject();
            message.put("role", "user");
            message.put("content", "Debug the following code snippet:\n" + codeSnippet);
            messages.put(message);
            jsonBody.put("messages", messages);

            RequestBody body = RequestBody.create(mediaType, jsonBody.toString());
            Request request = new Request.Builder()
                    .url(OPENAI_API_URL)
                    .post(body)
                    .addHeader("Authorization", "Bearer " + API_KEY)
                    .addHeader("Content-Type", "application/json")
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    return "Error: OpenAI API request failed with status code " + response.code();
                }
                JSONObject responseBody = new JSONObject(response.body().string());
                JSONArray choices = responseBody.getJSONArray("choices");
                JSONObject choice = choices.getJSONObject(0);
                String debugResult = choice.getJSONObject("message").getString("content");
                return "Debugging result:\n" + debugResult;
            } catch (IOException e) {
                return "Error: " + e.getMessage();
            }
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}
