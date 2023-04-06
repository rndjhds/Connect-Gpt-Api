package com.example.GptApi.service;

import com.example.GptApi.model.ChatCompletion;
import com.example.GptApi.model.Message;
import com.example.GptApi.repository.GptRepository;
import com.example.GptApi.utils.HttpUtil;
import com.example.GptApi.utils.JsonUtil;
import com.example.GptApi.utils.Stringutil;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

@Service
@Slf4j
public class GptServiceImpl implements GptService {

    private final GptRepository gptRepository;

    public GptServiceImpl(GptRepository gptRepository) {
        this.gptRepository = gptRepository;
    }

    @Value("${open_ai_key}")
    private String openAiKey;

    @Value("${open_ai_url}")
    private String openAiUrl;

    @Override
    public String responseGptApi(String message) {

        try {

            Message gptRequestMessages = new Message("user", message);

            gptRepository.saveGptResponse(gptRequestMessages);
            log.info("!!!!!!!! {}", gptRepository.listGptRequestMessages());

            ChatCompletion chatCompletion = new ChatCompletion(gptRepository.listGptRequestMessages());

            String gptRequestJsonObject = JsonUtil.getJson(chatCompletion);

            HttpEntity<String> httpEntity = HttpUtil.getStringHttpEntity(gptRequestJsonObject, openAiKey);
            log.info("GPT 요청 데이터 : {}", httpEntity.toString());

            String body = HttpUtil.getStringResponseEntity(httpEntity, openAiUrl);
            log.info("GPT 응답 데이터 : {}", body);

            JsonObject gptResponseJsonObject = JsonUtil.getJsonObject(body);

            Message requestMessages = Stringutil.getResult(gptResponseJsonObject);
            gptRepository.saveGptResponse(requestMessages);

            return requestMessages.getContent();
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
