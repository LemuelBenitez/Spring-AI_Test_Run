package com.example.test.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat-api")
public class ChatController {
    private final ChatClient chatClient;

    public ChatController(@Qualifier("openAiChatClient") ChatClient.Builder chat){
        this.chatClient = chat.build();
    }

    @GetMapping("/chat")
    public String chat(@RequestParam("message")String message){
       return chatClient.prompt(message).call().content();
    }
}
