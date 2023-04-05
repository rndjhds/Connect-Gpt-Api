package com.example.GptApi.model;

public class Message {

    private String role;

    private String content;

    @Override
    public String toString() {
        return "GptRequestMessages{" +
                "role='" + role + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
