package com.example;

import okhttp3.*;
import org.json.JSONObject;
import org.json.JSONArray;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {
    private static final String API_KEY = System.getenv("OPENAI_API_KEY");

    public static void main(String[] args) {
        if (API_KEY == null || API_KEY.isEmpty()) {
            System.out.println("Error: API key is not set. Please configure the OPENAI_API_KEY environment variable.");
            return;
        }
    
        Scanner scanner = new Scanner(System.in);
        try {
            while (true) {  // Infinite loop to allow multiple entries
                System.out.println("Enter the code snippets to debug (separate multiple snippets with a semicolon ';' or type 'exit' to quit):");
                String input = scanner.nextLine().trim();

                if (input.equalsIgnoreCase("exit")) {
                    System.out.println("Exiting the debugger. Goodbye!");
                    break;
                }

                // Split the input into multiple snippets
                String[] snippets = input.split(";");
                for (String snippet : snippets) {
                    snippet = snippet.trim();
                    if (snippet.isEmpty()) continue;

                    System.out.println("\nDebugging snippet:\n" + snippet);
                    try {
                        String response = getDebugSuggestions(snippet);
                        System.out.println("GPT Suggestions:\n" + response);
                    } catch (IOException e) {
                        System.out.println("Error: Unable to call OpenAI API for snippet:\n" + snippet);
                        e.printStackTrace();
                    }
                }
            }
        } finally {
            scanner.close();
        }
    }

    public static String getDebugSuggestions(String code) throws IOException {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        String apiUrl = "https://api.openai.com/v1/chat/completions";
        String requestBody = createRequestBody(code);

        Request request = new Request.Builder()
                .url(apiUrl)
                .post(RequestBody.create(requestBody, MediaType.parse("application/json")))
                .addHeader("Authorization", "Bearer " + API_KEY)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                String responseBody = response.body().string();
                JSONObject jsonResponse = new JSONObject(responseBody);
                return jsonResponse
                        .getJSONArray("choices")
                        .getJSONObject(0)
                        .getJSONObject("message")
                        .getString("content");
            } else {
                throw new IOException("Unexpected response: " + response.code() + " - " + response.message());
            }
        } finally {
            client.connectionPool().evictAll(); // Close idle connections
            client.dispatcher().executorService().shutdown(); // Shut down the executor
        }
    }

    private static String createRequestBody(String code) {
        JSONObject request = new JSONObject();
        request.put("model", "gpt-3.5-turbo");

        JSONArray messages = new JSONArray();
        messages.put(new JSONObject().put("role", "system").put("content", "You are a helpful assistant."));
        messages.put(new JSONObject().put("role", "user").put("content", "Debug this code:\n" + code));

        request.put("messages", messages);
        request.put("max_tokens", 150);

        return request.toString();
    }
}