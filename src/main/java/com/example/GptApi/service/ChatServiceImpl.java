package com.example.GptApi.service;

import com.example.GptApi.model.ChatCompletion;
import com.example.GptApi.model.Message;
import com.example.GptApi.repository.ChatRepository;
import com.example.GptApi.utils.HttpUtil;
import com.example.GptApi.utils.JsonUtil;
import com.example.GptApi.utils.openAIUtil;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

@Service
@Slf4j
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;

    public ChatServiceImpl(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    @Value("${open_ai_key}")
    private String openAiKey;

    @Value("${open_ai_url}")
    private String openAiUrl;

    //
    @Override
    public String responseGptApi(String message) {

        try {

            saveMemoryRepository("user", message);

            ChatCompletion chatCompletion = new ChatCompletion(chatRepository.listGptRequestMessages());
            String chatCompletionToJson = JsonUtil.createObjectToJson(chatCompletion);

            HttpEntity<String> httpEntity = HttpUtil.createStringHttpEntity(chatCompletionToJson, openAiKey);
            log.info("GPT 요청 데이터 : {}", httpEntity.toString());

            String chatResponseEntityBody = HttpUtil.createStringResponseEntity(httpEntity, openAiUrl);
            log.info("GPT 응답 데이터 : {}", chatResponseEntityBody);

            JsonObject chatToJsonObject = JsonUtil.createStringToJsonObject(chatResponseEntityBody);

            String chatContent = openAIUtil.createStringFromJsonObject(chatToJsonObject, "content");
            String chatRole = openAIUtil.createStringFromJsonObject(chatToJsonObject,"role");
            saveMemoryRepository(chatRole, chatContent);

            return chatContent;

        } catch (RestClientException e) {
            return "서버 응답 에러가 발생했습니다.";
        } catch (JsonSyntaxException e) {
            return "JSON 요소가 잘못 구성되어 있거나 문법적으로 잘못되어서 발생했습니다..";
        } catch (IllegalStateException e) {
            return "JsonElement가 JsonObject인지 확인해 주세요.";
        } catch (OutOfMemoryError e){
            return "메모리를 확인해주세요";
        }
    }

    private void saveMemoryRepository(String role, String content) {
        Message gptRequestMessage = new Message(role, content);
        chatRepository.save(gptRequestMessage);
    }
}
