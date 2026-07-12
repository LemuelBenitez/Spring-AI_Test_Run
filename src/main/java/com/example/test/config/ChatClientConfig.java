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
     then inject it where needed.  */

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

  /*
    Message Roles :
      -  User :  What the requester is asking the model to do.  This is the most common role.
      - System :  Instructions to the model about how to behave.  This is often used to set the tone or style of the model’s responses.
      - Assistant :  The model’s responses to the user’s requests.  This is the role that the model will use when
      generating its output.  The assistant role is typically used in the model’s responses, but it can also be used in
       the user’s requests to provide context or instructions to the model.
      - Function : Special instructions to run a function or fetch data.
      - Tool :  The model can use tools to perform specific tasks or retrieve information.  The tool role is used to
      indicate that the model is using a tool, and the tool’s response will be returned to the user.  The tool role is
       typically used in the model’s responses, but it can also be used in the user’s requests to provide context or
      - System Tool :  The model can use system tools to perform specific tasks or retrieve information.
      The system tool role is used to indicate that the model is using a system tool, and the system tool’s response
      will be returned to the user.  The system tool role is typically used in the model’s responses, but it can also
      be used in the system’s requests to provide context or instructions to the model.l’s response will

      Example:

      System: "You are a friendly tour guide."
      User: "What are the top 3 places to visit in rome ?"
      Assistant:"Sure! The top 3 are ..."
      Function: "Sending Kafka Request and waiting for a response from corresponding api..."

      Not All LLm model providers support all roles. i.e Google Gemini No System/Function roles. Only user, model (like Assistant)
   */



}
