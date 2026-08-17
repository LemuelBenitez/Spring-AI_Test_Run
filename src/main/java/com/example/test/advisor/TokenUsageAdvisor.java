package com.example.test.advisor;

import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.CallAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAdvisorChain;
import org.springframework.ai.chat.metadata.Usage;
import org.springframework.ai.chat.model.ChatResponse;

import java.util.logging.Logger;

import static java.util.Objects.isNull;

public class TokenUsageAdvisor  implements CallAdvisor {
    private static Logger logger = Logger.getLogger(TokenUsageAdvisor.class.getName());

    @Override
    public ChatClientResponse adviseCall(ChatClientRequest chatClientRequest, CallAdvisorChain callAdvisorChain) {
       /*Copied from SimpleLoggerAdvisor to maintain immutability of the request and response objects.
          - This is important for thread safety and to avoid side effects.
        */
        ChatClientResponse chatClientResponse = callAdvisorChain.nextCall(chatClientRequest);
        ChatResponse chatResponse =  chatClientResponse.chatResponse();

        if(!isNull(chatResponse.getMetadata()) && !isNull(chatResponse.getMetadata().getRateLimit())){
            /*
            All LLM's store thier metadata differently, so we need to check for nulls before accessing the usage data.
             */
            Usage usage = chatResponse.getMetadata().getUsage();
            logger.info(usage.toString());
        }
        return chatClientResponse;
    }

    @Override
    public String getName() {
    //Spring AI strictly enforce that every custom advisor provides a valid name.
        return this.getClass().getSimpleName();
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
