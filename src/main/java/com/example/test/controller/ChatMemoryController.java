package com.example.test.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/chat-memory")
public class ChatMemoryController {
    private final ChatClient.Builder openAI;

    /*
    ChatMemory: Defines what to store (e.g last N messages)
      - Sample implementation class `MessageWindowChatMemory.class`

    ChatMessageRepository: Defines contract for storing and retrieving chat messages
      -  Using InmemoryChatMemoreRepository we can store the chat memory inside in-memory (default)
      -  Using JdbcChatMemoryRepository we can store the chat memory in a DB like H2, MySQL, PostgreSQL, etc.

    Advisors are used to making ChatClient's Remember
    Adviosrs manage how memory is stored and reused across multiple interactions.

    1. MessageChatMemoryAdvisor
      - Stores chat as a list of structured messages
      - Inject past messages directly into prompt
      - Best when you want the LLM to see the full chat history like a real chat log

    2. PromptChatMemoryAdvisor
      - Converts memory into plain text format
      - Appends it to the system prompt (like a summary)
      - Good for simple LLMs or when token budget is limited

     3. VectorChatMemoryAdvisor
      - Stores chat as vector embeddings
      - Uses a vector database to retrieve relevant past messages
      - Best for semantic search and retrieval of past messages
      - Lon-term or knowledge-based chats
     */
    public ChatMemoryController(@Qualifier("chatMemoryChatClient") ChatClient.Builder openAI) {
        this.openAI = openAI;
    }

    @GetMapping("/chat-memory")
    public ResponseEntity<String> chatMemory(@RequestParam String message) {
        return ResponseEntity.ok(openAI.build().prompt().user(message)
                .advisors(advisorSpec -> advisorSpec.param(ChatMemory.CONVERSATION_ID, "default"))
                .call().content());
        /*
        Drawbacks:
        - MessageChatMemoryAdvisor: Can lead to long prompts if the chat history is extensive, potentially exceeding token limits.
        - Multiple users: If multiple users are interacting with the same chat memory, it can lead to confusion and mixed responses.
          It's essential to manage conversation IDs properly.
        - 'Default' ensures that all users share the same chat memory. In a multi-user environment, consider using
           unique conversation IDs for each user/session to avoid mixing conversations. In order to fix this we can
            use a unique conversation ID for each user/session. For example, you can generate a unique ID based on the
            user's session or user ID and pass it as a parameter to the advisorSpec. This way, each user will have their
            own chat memory, and their conversations won't interfere with each other.
           - Using a 'ConcurrentHashMap' as a storage mechanism for chat memories can help manage multiple users' chat
            histories effectively. Each user's conversation ID can map to their respective chat memory, ensuring
            isolation and preventing cross-talk between different users' conversations.
         */
    }

    @GetMapping("/chat-memory-username")
    public ResponseEntity<String> chatMemory(@RequestParam("username") String username, String message) {
        return ResponseEntity.ok(openAI.build().prompt().user(message)
                .advisors(advisorSpec -> advisorSpec.param(ChatMemory.CONVERSATION_ID, username))
                .call().content());

            /*
         Below ConcurrentHashMap is used in  under the hood when using InmemoryChatMemoryRepository to store the chat
        memory inside in-memory (username instead of default).

        private ConcurrentHashMap<String, List<Message>> chatMemoryMap = new ConcurrentHashMap<>();

        username is the key.
             */

    }

    /*
    For long term memory we would want to use a DB.
    - Above method can be used to store chat memory in a DB like H2, MySQL, PostgreSQL, etc.
    by using JdbcChatMemoryRepository instead of InmemoryChatMemoryRepository(which uses a ConcurrentHashMap).
     */
}
