package com.example.GptApi.service;

import com.example.GptApi.model.ChatCompletion;
import com.example.GptApi.model.Message;
import com.example.GptApi.repository.GptRepository;
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

@Service
@Slf4j
public class GptServiceImpl implements GptService {

    private final GptRepository gptRepository;

    public GptServiceImpl(GptRepository gptRepository) {
        this.gptRepository = gptRepository;
    }

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

            Message gptRequestMessages = new Message(); // GptRequestMessages의 객체 생성
            gptRequestMessages.setRole("user");
            gptRequestMessages.setContent(message); // GptRequestMessages의 인스턴스에 필드 값 set

            gptRepository.saveGptResponse(gptRequestMessages); // GptRequestMessages의 객체 List메모리 저장소에 저장
            log.info("!!!!!!!! {}", gptRepository.listGptRequestMessages());

            ChatCompletion chatCompletion = new ChatCompletion(); // GptRequest의 객체 생성
            chatCompletion.setMessages(gptRepository.listGptRequestMessages());  // GptRequest인스턴스의 Message에 List메모리 저장소 저장

            Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create(); // Gson 객체 생성
            String gptRequestJsonObject = gson.toJson(chatCompletion); // gptRequest를 Json형식으로 변형

            HttpHeaders headers = new HttpHeaders(); // HttpClient의 header 객체 생성
            headers.setBearerAuth(openAiKey); // header의 인증키 set
            headers.add("Content-type", "application/json"); // header의 Content-type set

            HttpEntity<String> httpEntity = new HttpEntity<>(gptRequestJsonObject, headers); // HttpClient 전송을 위한 header와 body 설정
            String openAiUrl = "https://api.openai.com/v1/chat/completions"; // open Ai의 URL 설정
            RestTemplate restTemplate = new RestTemplate(); // spring 프레임워크의 http call 메서드 생성

            log.info("GPT 요청 데이터 : " + httpEntity.toString());

            ResponseEntity<String> gptResponseEntity = restTemplate.exchange(openAiUrl, HttpMethod.POST, httpEntity, String.class); // 요청 보내기 후 응답 받음
            // 요청 전송
            log.info("GPT 응답 데이터 : " + gptResponseEntity.getBody());

            JsonObject gptResponseJsonObject = gson.fromJson(gptResponseEntity.getBody(), JsonObject.class); // String형태로 넘어온 body값 JsonObject로 변형

            String response = "";
            String role = "";
            for (JsonElement gptResponseJsonElement : gptResponseJsonObject.getAsJsonArray("choices")) {
                JsonElement gptResponseMessage = gptResponseJsonElement.getAsJsonObject().get("message");
                response = gptResponseMessage.getAsJsonObject().get("content").getAsString();
                role =  gptResponseMessage.getAsJsonObject().get("role").getAsString();
            }

            Message requestMessages = new Message();
            requestMessages.setContent(response);
            requestMessages.setRole(role);
            gptRepository.saveGptResponse(requestMessages);

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
