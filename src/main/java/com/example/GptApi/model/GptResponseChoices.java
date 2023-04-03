package com.example.GptApi.model;

public class GptResponseChoices {

    private String index;

    private GptRequestMessages message;

    private String finishReason;

    @Override
    public String toString() {
        return "GptResponseChoices{" +
                "index='" + index + '\'' +
                ", messages=" + message +
                ", finishReason='" + finishReason + '\'' +
                '}';
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public GptRequestMessages getMessage() {
        return message;
    }

    public void setMessage(GptRequestMessages message) {
        this.message = message;
    }

    public String getFinishReason() {
        return finishReason;
    }

    public void setFinishReason(String finishReason) {
        this.finishReason = finishReason;
    }
}
