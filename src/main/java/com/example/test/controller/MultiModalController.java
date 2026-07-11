package com.example.test.controller;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

@RestController("/api/v2")
public class MultiModalController {
    private ChatClient openAiChatClient;
    private ChatClient ollamaChatClient;
    private ChatClient bedrockClaudeChatClient;

    public MultiModalController(
            @Qualifier("openAiChatClient")ChatClient.Builder openAiChatClient,
            @Qualifier("ollamaChatClient") ChatClient.Builder ollamaChatModel,
            @Qualifier("bedrockClaudeChatClient") ChatClient.Builder bedrockClaudeChatModel){
        this.ollamaChatClient = ollamaChatModel.build();
        this.openAiChatClient = openAiChatClient.build();
        this.bedrockClaudeChatClient = bedrockClaudeChatModel.build();
    }

    @GetMapping("/ollama")
    public String ollamaResponse(@RequestParam String msg){
        return ollamaChatClient.prompt(msg).call().content();
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
