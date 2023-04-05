package com.example.GptApi.repository;

import com.example.GptApi.model.Message;

import java.util.List;

public interface GptRepository {

    public void saveGptResponse(Message message);

    public List<Message> listGptRequestMessages();
}
