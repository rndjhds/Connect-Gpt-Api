package com.example.GptApi.service;

import com.example.GptApi.model.GptRequest;
import com.example.GptApi.model.GptRequestMessages;
import com.google.gson.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class GptServiceImpl implements GptService {

    @Value("${open_ai_key}")
    private String openAiKey;

    @Override
    public String responseGptApi(String message) {

        try {
            log.info("GPT로 넘어갈 요청 데이터 : " + message);

            GptRequest gptRequest = new GptRequest();
            gptRequest.setModel("gpt-3.5-turbo");

            GptRequestMessages gptRequestMessages = new GptRequestMessages();
            gptRequestMessages.setRole("user");
            gptRequestMessages.setContent(message);

            List<GptRequestMessages> gptRequestMessagesList = new ArrayList<>();
            gptRequestMessagesList.add(gptRequestMessages);
            gptRequest.setMessages(gptRequestMessagesList);

            Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
            String gptRequestJsonObject = gson.toJson(gptRequest);

            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(openAiKey);
            headers.add("Content-type", "application/json");

            HttpEntity<String> httpEntity = new HttpEntity<>(gptRequestJsonObject, headers);
            String openAiUrl = "https://api.openai.com/v1/chat/completions";
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> gptResponseEntity = restTemplate.exchange(openAiUrl, HttpMethod.POST, httpEntity, String.class);

            log.info("GPT에게 응답 받은 전체 response 데이터 : " + gptResponseEntity.getBody());

            JsonObject gptResponseJsonObject = gson.fromJson(gptResponseEntity.getBody(), JsonObject.class);

            String response = null;
            for (JsonElement gptResponseJsonElement : gptResponseJsonObject.getAsJsonArray("choices")) {
                JsonElement gptResponseMessage = gptResponseJsonElement.getAsJsonObject().get("message");
                response = gptResponseMessage.getAsJsonObject().get("content").getAsString();
            }

            log.info("GPT에게 응답 받은 요청에 대한 메시지 : " + response);

            return response;
        } catch (RestClientException e) {
            return "서버 응답 에러가 발생했습니다.";
        } catch (JsonSyntaxException e) {
            return "JSON 요소가 잘못 구성되어 있거나 문법적으로 잘못되어서 발생했습니다..";
        } catch (IllegalStateException e) {
            return "JsonElement가 JsonObject인지 확인해 주세요.";
        }
    }
}
