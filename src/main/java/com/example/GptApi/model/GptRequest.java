package com.example.GptApi.model;

import java.util.List;

public class GptRequest {

    private String model;

    private List<GptRequestMessages> messages;

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<GptRequestMessages> getMessages() {
        return messages;
    }

    public void setMessages(List<GptRequestMessages> messages) {
        this.messages = messages;
    }
}
