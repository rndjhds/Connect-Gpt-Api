package com.example.GptApi.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class openAIUtil {

    public static String createStringFromJsonObject(JsonObject gptResponseJsonObject, String body) {

        String response = "";
        for (JsonElement gptResponseJsonElement : gptResponseJsonObject.getAsJsonArray("choices")) {
            JsonElement gptResponseMessage = gptResponseJsonElement.getAsJsonObject().get("message");
            response = gptResponseMessage.getAsJsonObject().get(body).getAsString();
        }

        return response;
    }
}
