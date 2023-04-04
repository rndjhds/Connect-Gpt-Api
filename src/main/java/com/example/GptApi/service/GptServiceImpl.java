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
            // GPT 기본 설정 세팅
            // 입력내용을 -> JSON형식의 GPT용 요청 객체로 변환 ?..
            // HttpClient 설정 준비 ... (post, json, time oust.
            // GPT API 요청 전송
            // GPT API 응답 수신
            // 화면출력을 위한 메시지 추출 처리 ? 필요할 지모르겠네.


            GptRequest gptRequest = new GptRequest();
            gptRequest.setModel("gpt-3.5-turbo");
            // gptRequest set

            GptRequestMessages gptRequestMessages = new GptRequestMessages();
            gptRequestMessages.setRole("user");
            gptRequestMessages.setContent(message);
            // gptRequestMessage  set

            List<GptRequestMessages> gptRequestMessagesList = new ArrayList<>();
            gptRequestMessagesList.add(gptRequestMessages);
            gptRequest.setMessages(gptRequestMessagesList);
            // List에 gptRequestMessage set

            Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
            String gptRequestJsonObject = gson.toJson(gptRequest);
            // gson으로 gptReqeust를Json set

            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(openAiKey);
            headers.add("Content-type", "application/json");
            // http header set

            HttpEntity<String> httpEntity = new HttpEntity<>(gptRequestJsonObject, headers);
            String openAiUrl = "https://api.openai.com/v1/chat/completions";
            RestTemplate restTemplate = new RestTemplate();

            log.info("GPT 요청 데이터 : " +  httpEntity.toString());

            ResponseEntity<String> gptResponseEntity = restTemplate.exchange(openAiUrl, HttpMethod.POST, httpEntity, String.class);
            // 요청 전송
            log.info("GPT 응답 데이터 : " + gptResponseEntity.getBody());

            JsonObject gptResponseJsonObject = gson.fromJson(gptResponseEntity.getBody(), JsonObject.class);

            String response = null;
            for (JsonElement gptResponseJsonElement : gptResponseJsonObject.getAsJsonArray("choices")) {
                JsonElement gptResponseMessage = gptResponseJsonElement.getAsJsonObject().get("message");
                response = gptResponseMessage.getAsJsonObject().get("content").getAsString();
            }

            return response;
            // 응답 완료 후 데이터 처리
        } catch (RestClientException e) {
            return "서버 응답 에러가 발생했습니다.";
        } catch (JsonSyntaxException e) {
            return "JSON 요소가 잘못 구성되어 있거나 문법적으로 잘못되어서 발생했습니다..";
        } catch (IllegalStateException e) {
            return "JsonElement가 JsonObject인지 확인해 주세요.";
        }
    }
}
