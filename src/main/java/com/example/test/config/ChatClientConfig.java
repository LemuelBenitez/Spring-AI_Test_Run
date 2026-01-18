package com.example.test.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatClientConfig {

    public ChatClientConfig(){}

    /*
    In Spring AI the builder is provider-specific—there isn’t a single generic ChatModel.Builder.
     You create (and expose) the builder for the provider you use (OpenAI, Ollama, Bedrock),
     then inject it where needed.
     */

    @Bean
    public ChatClient openAiChatClient(OpenAiChatModel openAiChatModel){
        ChatClient.Builder chat = ChatClient.builder(openAiChatModel);
        return chat.build();
    }

    @Bean
    public ChatClient ollamaChatClient(OllamaChatModel ollamaChatModel){
        ChatClient.Builder chat = ChatClient.builder(ollamaChatModel);
        return chat.build();
    }
}
