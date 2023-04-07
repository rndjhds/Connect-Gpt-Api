package com.example.GptApi.repository;

import com.example.GptApi.model.Message;

import java.util.List;

public interface ChatRepository {

    public void saveGptResponse(Message message);

    public List<Message> listGptRequestMessages();
}
