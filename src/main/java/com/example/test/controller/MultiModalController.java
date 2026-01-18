package com.example.test.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.ResponseEntity;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

@RestController("/api/v2")
public class MultiModalController {
    private ChatClient openAiChatClient;
    private ChatClient ollamaChatClient;

    public MultiModalController(@Qualifier("openAiChatClient")ChatClient openAiChatClient,
                                           @Qualifier("ollamaChatClient") ChatClient ollamaChatModel){
        this.ollamaChatClient = ollamaChatModel;
        this.openAiChatClient = openAiChatClient;
    }

    @GetMapping("/ollama")
    public String ollamaResponse(@RequestParam String msg){
        return ollamaChatClient.prompt(msg).call().content();
    }

    @GetMapping("/openAI")
    public String openAiResponse(@RequestParam String msg){
        return openAiChatClient.prompt(msg).call().content();
    }
}
