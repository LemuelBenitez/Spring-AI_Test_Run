package com.example.test.config;

import org.springframework.ai.bedrock.converse.BedrockProxyChatModel;
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

    @Bean("openAiChatClient")
    public ChatClient.Builder openAiChatClient(OpenAiChatModel openAiChatModel){
        return ChatClient.builder(openAiChatModel);
    }

    @Bean("ollamaChatClient")
    public ChatClient.Builder ollamaChatClient(OllamaChatModel ollamaChatModel){
        return ChatClient.builder(ollamaChatModel);
    }

    @Bean("bedrockClaudeChatClient")
    public ChatClient.Builder bedrockClaudeChatClient(BedrockProxyChatModel bedrockClaudeChatModel){
        return ChatClient.builder(bedrockClaudeChatModel);
    }
}
