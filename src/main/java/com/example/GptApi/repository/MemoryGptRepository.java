package com.example.GptApi.repository;

import com.example.GptApi.model.Message;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MemoryGptRepository implements GptRepository {

    public static List<Message> messageList = new ArrayList<>();

    @Override
    public void saveGptResponse(Message RequestMessages) {
        messageList.add(RequestMessages);
    }

    @Override
    public List<Message> listGptRequestMessages() {
        return messageList;
    }
}

