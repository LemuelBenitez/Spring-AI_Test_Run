package com.example.test.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chat-api")
public class ChatController {
//    private final ChatClient chatClient;
//
//    public ChatController(ChatClient.Builder chat){
//        this.chatClient = chat.build();
//    }
//
//    @GetMapping("/chat/{message}")
//    public String chat(@PathVariable("message")String message){
//       return chatClient.prompt(message).call().content();
//    }
}
