package com.example.GptApi.model;

import java.util.ArrayList;
import java.util.List;

public class GptRequest {

    private String model;

    private List<GptRequestMessages> messages;

    @Override
    public String toString() {
        return "GptRequest{" +
                "model='" + model + '\'' +
                ", messages=" + messages +
                '}';
    }

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

    public void addMessage(GptRequestMessages messages) {
        this.messages.add(messages);
    }
}
