package com.example.GptApi.model;

public class GptResponseUsage {

    private String promptToken;

    private String completionTokens;

    private String totalTokens;

    public String getPromptToken() {
        return promptToken;
    }

    public void setPromptToken(String promptToken) {
        this.promptToken = promptToken;
    }

    public String getCompletionTokens() {
        return completionTokens;
    }

    public void setCompletionTokens(String completionTokens) {
        this.completionTokens = completionTokens;
    }

    public String getTotalTokens() {
        return totalTokens;
    }

    public void setTotalTokens(String totalTokens) {
        this.totalTokens = totalTokens;
    }
}
