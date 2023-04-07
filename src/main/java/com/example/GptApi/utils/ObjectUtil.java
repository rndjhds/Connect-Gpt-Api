package com.example.GptApi.utils;

import com.example.GptApi.model.Message;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class ObjectUtil {

    public static Message getResult(JsonObject gptResponseJsonObject) {

        // 목적
        // 1 : JsonObject에서 특정 데이터를 객체의 형태로 만들기
        // 2 : JsonObject의 형태 {"a" : "1", "b" : [{ "b-1" : "9", "b-2" : "10" }]}
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
