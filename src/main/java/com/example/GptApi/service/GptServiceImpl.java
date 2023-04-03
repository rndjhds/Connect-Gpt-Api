package com.example.GptApi.service;

import com.example.GptApi.model.GptRequest;
import com.example.GptApi.model.GptRequestMessages;
import com.example.GptApi.model.GptResponse;
import com.google.gson.*;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class GptServiceImpl implements GptService {

    @Override
    public String responseGptApi(String message) {
        log.info("GPT로 넘어갈 요청 데이터 : " + message);
        GptRequest gptRequest = new GptRequest();
        gptRequest.setModel("gpt-3.5-turbo");
        List<GptRequestMessages> list = new ArrayList<>();
        GptRequestMessages gptRequestMessages = new GptRequestMessages();
        gptRequestMessages.setRole("user");
        gptRequestMessages.setContent(message);
        list.add(gptRequestMessages);
        gptRequest.setMessages(list);

        Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
        String jsonObject = gson.toJson(gptRequest);

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth("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");
        headers.add("Content-type", "application/json");

        HttpEntity<String> httpEntity = new HttpEntity<>(jsonObject, headers);
        ResponseEntity<String> response = restTemplate.exchange("https://api.openai.com/v1/chat/completions", HttpMethod.POST, httpEntity, String.class);

        log.info(response.getBody());

        JsonObject gptResponse = gson.fromJson(response.getBody(), JsonObject.class);

        String text = null;
        for (JsonElement jsonElement : gptResponse.getAsJsonArray("choices")){
            JsonElement message1 = jsonElement.getAsJsonObject().get("message");
            text = message1.getAsJsonObject().get("content").getAsString();
        }

        return text;
    }
}
