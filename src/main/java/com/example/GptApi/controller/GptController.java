package com.example.GptApi.controller;

import com.example.GptApi.model.GptResponse;
import com.example.GptApi.service.GptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
public class GptController {

    @Autowired
    private GptService gptService;

    @GetMapping("/requestForm")
    public String requestForm() {
        return "requestForm";
    }

    @PostMapping("/requestMessageToGpt")
    @ResponseBody
    public String  requestMessageToGpt(@RequestParam("message") String message) {
        log.info("요청 데이터 : " + message);

        String  getResponse = gptService.responseGptApi(message);

        return getResponse;
    }
}
