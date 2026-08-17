package com.example.test.config;

import com.example.test.repository.CustomChatHistoryDB;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.memory.repository.jdbc.JdbcChatMemoryRepository;
import org.springframework.ai.chat.memory.repository.jdbc.JdbcChatMemoryRepositoryDialect;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

@Configuration
public class ChatMemoryChatClientConfig {

    @Bean
    public ChatClient.Builder chatMemoryChatClient(OpenAiChatModel openAiChatModel, ChatMemory chatMemory) {
       Advisor memoryAdvisor =  MessageChatMemoryAdvisor.builder(chatMemory).build();
       Advisor advisor = new SimpleLoggerAdvisor();
        return ChatClient.builder(openAiChatModel).defaultAdvisors(List.of(advisor,memoryAdvisor));
    }

    @Bean
    public JdbcChatMemoryRepository chatMemoryRepository(JdbcTemplate jdbcTemplate) {
        return JdbcChatMemoryRepository.builder()
                .jdbcTemplate(jdbcTemplate)
                .dialect(new CustomChatHistoryDB()) // Inject your custom table names here
                .build();
    }

    @Bean
    ChatMemory chatMemory(JdbcChatMemoryRepository jdbcChatMemoryRepository) {
       /*
          By default the maximum message is 20 in MessageWindowChatMemory, to change this we can use the builder and
          set the maxMessages to a different value. For example, to set it to 50:
        */

        return MessageWindowChatMemory.builder()
                .chatMemoryRepository(jdbcChatMemoryRepository)
                .maxMessages(50) // Set the maximum number of messages to store in memory
                .build();

    }
}
