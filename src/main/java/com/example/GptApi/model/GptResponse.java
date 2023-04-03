package com.example.GptApi.model;

import java.util.List;

public class GptResponse {

    private String id;

    private String object;

    private String created;

    private List<GptResponseChoices> choices;

    private GptResponseUsage usage;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public List<GptResponseChoices> getChoices() {
        return choices;
    }

    public void setChoices(List<GptResponseChoices> choices) {
        this.choices = choices;
    }

    public GptResponseUsage getUsage() {
        return usage;
    }

    public void setUsage(GptResponseUsage usage) {
        this.usage = usage;
    }
}
