package com.example.GptApi.repository;

import com.example.GptApi.model.GptRequest;
import com.example.GptApi.model.GptRequestMessages;

import java.util.List;

public interface GptRepository {

    public void saveGptResponse(GptRequestMessages gptRequestMessages);

    public GptRequest listGptRequestMessages();
}
