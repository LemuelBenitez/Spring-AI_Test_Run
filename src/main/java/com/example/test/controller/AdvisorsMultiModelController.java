package com.example.test.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/prompt-advisors")
public class AdvisorsMultiModelController {
    private ChatClient openAiChatClient;
    private ChatClient ollamaChatClient;
    private ChatClient bedrockClaudeChatClient;

    public AdvisorsMultiModelController(
            @Qualifier("openAiChatClient")ChatClient.Builder openAiChatClient,
            @Qualifier("ollamaChatClient") ChatClient.Builder ollamaChatModel,
            @Qualifier("bedrockClaudeChatClient") ChatClient.Builder bedrockClaudeChatModel){
        this.ollamaChatClient = ollamaChatModel.build();
        this.openAiChatClient = openAiChatClient.build();
        this.bedrockClaudeChatClient = bedrockClaudeChatModel.build();
    }

    @GetMapping("/ollama")
    public String ollamaResponse(@RequestParam String msg){
        return ollamaChatClient.prompt()
                .system("You are a helpful Customer Support assistant. If a user asks a question, let them know you are still under construction.")
                .user(msg)
                .call().content();
    }

    @GetMapping("/openAI")
    public String openAiResponse(@RequestParam String msg){
        return openAiChatClient.prompt(msg).call().content();
    }

    @GetMapping("/aws-bedrock")
    public String bedrockResponse(@RequestParam String msg){
        return bedrockClaudeChatClient.prompt(msg).call().content();
    }
}
