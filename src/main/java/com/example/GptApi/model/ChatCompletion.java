package com.example.GptApi.model;

import java.util.List;

public class ChatCompletion {

    private final String model = "gpt-3.5-turbo";

    private List<Message> messages;

    public ChatCompletion(List<Message> messages) {
        this.messages = messages;
    }

    public ChatCompletion() {
    }

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

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public void addMessage(Message messages) {
        this.messages.add(messages);
    }
}
