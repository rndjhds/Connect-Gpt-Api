package com.example.GptApi.controller;

import com.example.GptApi.service.ChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("/requestForm")
    public String requestForm() {
        return "requestForm";
    }

    @PostMapping("/requestMessageToGpt")
    @ResponseBody
    public String requestMessageToGpt(@RequestParam("requestMessage") String requestMessage) {

        log.info("요청 데이터 : {}", requestMessage);

        String responseMessage = chatService.responseGptApi(requestMessage);

        log.info("응답 데이터 : {}", responseMessage);

        return responseMessage;
    }
}
