package com.example.GptApi.utils;

import com.example.GptApi.model.Message;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Stringutil {

    public static Message getResult(JsonObject gptResponseJsonObject) {

        String response = "";
        String role = "";
        for (JsonElement gptResponseJsonElement : gptResponseJsonObject.getAsJsonArray("choices")) {
            JsonElement gptResponseMessage = gptResponseJsonElement.getAsJsonObject().get("message");
            response = gptResponseMessage.getAsJsonObject().get("content").getAsString();
            role = gptResponseMessage.getAsJsonObject().get("role").getAsString();
        }
        Message requestMessages = new Message();
        requestMessages.setContent(response);
        requestMessages.setRole(role);

        return requestMessages;
    }
}
