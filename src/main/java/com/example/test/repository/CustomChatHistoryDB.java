package com.example.test.repository;

import org.springframework.ai.chat.memory.repository.jdbc.JdbcChatMemoryRepositoryDialect;

public class CustomChatHistoryDB implements JdbcChatMemoryRepositoryDialect {
/*
 Here we are creating our own custom table name for chat history.
  The default table name is "spring_ai_memory" but we are changing it to "spring_ai_chat_history" in this example.
 */
    @Override
    public String getInsertMessageSql() {
        return "INSERT INTO spring_ai_chat_history (conversation_id, content, type, timestamp)" +
                " VALUES (?, ?, ?::message_type, ?)";
    }

    @Override
    public String getSelectMessagesSql() {
        return "SELECT content, type FROM spring_ai_chat_history WHERE conversation_id = ? ORDER BY timestamp ASC";
    }

    @Override
    public String getSelectConversationIdsSql() {
        return "SELECT conversation_id FROM spring_ai_chat_history WHERE conversation_id = ?";
    }

    @Override
    public String getDeleteMessagesSql() {
        return "DELETE FROM spring_ai_chat_history WHERE conversation_id = ?";
    }
}
