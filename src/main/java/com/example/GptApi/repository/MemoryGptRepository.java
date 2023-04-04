package com.example.GptApi.repository;

import com.example.GptApi.model.GptRequest;
import com.example.GptApi.model.GptRequestMessages;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MemoryGptRepository implements GptRepository {

    private static List<GptRequestMessages> gptRequestMessagesList = new ArrayList<>();

    @Override
    public void saveGptResponse(GptRequestMessages RequestMessages) {
        gptRequestMessagesList.add(RequestMessages);
    }

    @Override
    public GptRequest listGptRequestMessages() {

        GptRequest gptRequest = new GptRequest();
        gptRequest.setModel("gpt-3.5-turbo");
        for (int i = 0; i < gptRequestMessagesList.size(); i++) {
            gptRequest.addMessage(gptRequestMessagesList.get(i));
        }

        return gptRequest;
    }
}

