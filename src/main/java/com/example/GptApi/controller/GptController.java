package com.example.GptApi.controller;

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
    public String requestMessageToGpt(@RequestParam("requestMessage") String requestMessage) {

        log.info("요청 데이터 : " + requestMessage);

        String responseMessage = gptService.responseGptApi(requestMessage);

        log.info("응답 데이터 : " + responseMessage);

        return responseMessage;
    }
}
