package com.example.test.controller;

import com.example.test.advisor.TokenUsageAdvisor;
import com.example.test.model.BooksModel;
import org.springframework.ai.bedrock.converse.BedrockChatOptions;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.converter.ListOutputConverter;
import org.springframework.ai.converter.MapOutputConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/promptTemplate")
public class PromptTemplateController {
    /*
    NOTE: ChatClient instances
    - chatClient: The main client interface for sending prompts to LLMs
    - Used to build prompts, apply advisors, and retrieve responses from various AI providers
    - Can be configured with default system prompts, options, and advisors
     */
    private ChatClient openAiChatClient;
    private ChatClient ollamaChatClient;
    private ChatClient bedrockClaudeChatClient;
    private ChatClient bedrockClaudeChatClient2;


    @Value("classpath:prompts/customerSupport_PROD_Template_Example.st")
    private Resource promptTemplateResource;
    private String awsModelARN;

    public PromptTemplateController(
            @Qualifier("openAiChatClient")ChatClient.Builder openAiChatClient,
            @Qualifier("ollamaChatClient") ChatClient.Builder ollamaChatModel,
            @Qualifier("bedrockClaudeChatClient") ChatClient.Builder bedrockClaudeChatModel,
            @Qualifier("bedrockClaudeChatClient2") ChatClient.Builder bedrockClaudeChatModel2,
            @Value("${spring.ai.bedrock.converse.chat.options.model}") String awsModelARN){
        /*
        defaults allows us to set custom system and user prompts.  This is useful for setting the tone of the model's responses.
        The model uses the defaults to generate its responses, but they can be overridden by the user prompt.
         */
        this.ollamaChatClient = ollamaChatModel.
                defaultSystem("""
        You are a helpful Customer Support assistant. 
        If a user asks a question, let them know you are still under construction.
        """)
                .defaultUser("How can you help me ?").build();

        /*
        Prompt templates are best practice allowing us to store our prompts separately and autowire them in.
        Prompt templates are stored in a "st"(String Template) file, under /resources/promptTemplate
         */
        this.openAiChatClient = openAiChatClient
                .defaultSystem("""
        You are a helpful Customer Support assistant. 
        If a user asks a question, let them know you are still under construction.
        """)
                .defaultUser("How can you help me ?").build();


        /*
        In spring AI, advisors are like http interceptors or middle ware for your prompt flow.

        User -> ChatClient ->  [Advisors] -> LLM -> Response -> [Advisors] -> User

        Best Practices:
        - Keep advisors stateless or request-scoped
        - Chain multiple advisors if needed
        - Avoid altering the meaning of prompts unless intentional
        - Use advisors for cross-cutting concerns not core logic

        Spring AI built in Advisors: SimpleLoggerAdvisors, SafeGuardAdvisors(blocks unsafe words to LLM), PromptChatMemoryAdvisors, etc.
         */
        this.bedrockClaudeChatClient = bedrockClaudeChatModel
                .defaultSystem("""
        You are a helpful Customer Support assistant. 
        If a user asks a question, let them know you are still under construction.
        """)
                .defaultAdvisors(List.of(new TokenUsageAdvisor(), new SimpleLoggerAdvisor())).build();

        // ChatOptions Bean
        // For aws you need the ARN, chatGpt you can use the model name
        this.awsModelARN = awsModelARN;
//        var options = BedrockChatOptions.builder().model(this.awsModelARN).maxTokens(3)
//                .temperature(0.5).build();
        this.bedrockClaudeChatClient2 = bedrockClaudeChatModel2
//                .defaultSystem("""
//        You are a helpful Customer Support assistant.
//        If a user asks a question, let them know you are still under construction.
//        """)
                .defaultAdvisors(List.of(new TokenUsageAdvisor(), new SimpleLoggerAdvisor())).build();

    }

    @GetMapping("/default/customerServiceResponse/basic-prompt")
    public String ollamaResponse(@RequestParam String msg){
        /*
         defaults can be overridden using system.
         */
        return ollamaChatClient.prompt()
                .user(msg)
                .call().content();
    }

    @GetMapping("/prompt/customerServiceResponse/using-prompt-template")
    public String customerServiceResponse(@RequestParam("customerName") String customerName,
                                          @RequestParam("question") String question){
        /*
        If we want our prompTemplate to accept special characters (not {}), we can use StTemplateRenderer.

        Ex:

        Prompt promptTemplate = PromptTemplate.builder()
                                              .renderer(StTemplate.builder.startDelimiing("<<").endDelimiting(">>").build())
                                              .template("""
                                              Tell me the names of 5 movies whose soundTrack was composed by <<composer>>
                                              """)
                                              .build();

        Prompt Stuffing = Giving the LLM an open book before answering a question. (RAG is the more advanced version of this)
          - You include contextual data or refrence text along with the user's question.
          - The LLM uses this information to generate a more accurate and relevant response - even if it was not pre-trained on the topic.
          - This techniques is also known as in-context learning or retrieval-augmented prompting (when done programmatically).
          - Only good for limited amount of data.  If you have a lot of data, use RAG (Retrieval Augmented Generation) instead.

         Reasons for Limitations of PromptStuffing:
          - LLMs have a maximum token limit (e.g., 4096 tokens for GPT-3.5, 8192 tokens for GPT-4). If the combined
          length of the prompt and the context exceeds this limit, the model will truncate the input, potentially losing important information.
          - Prompt stuffing can lead to information overload, making it difficult for the LLM to focus on the most relevant details.
         */

        return openAiChatClient.prompt()
                .user(promptTemplateResource ->
                        promptTemplateResource.param("customerName",customerName)
                                .param("question",question))
                .call().content();

    }

    @GetMapping("/aws-bedrock/customerServiceResponse/using-advisors")
    public String bedrockResponse(@RequestParam String msg){
        /*
        adviceCall()
         - is used to apply the advisors to the prompt before sending it to the LLM.
         - This allows us to modify the prompt or add additional context before it is sent to the LLM.

         adviceStream()
            - is used to apply the advisors to the prompt before sending it to the LLM, but it returns a stream of responses from the LLM.
            - This allows us to process the responses as they are generated, rather than waiting for the entire response to be generated before processing it.
         */

//        return bedrockClaudeChatClient.prompt(msg)
//                .advisors(List.of(new TokenUsageAdvisor())).call().content();
        return bedrockClaudeChatClient.prompt(msg).call().content();
    }

    // TODO: run to test streaming -> http://localhost:8080/promptTemplate/aws-bedrock/customerServiceResponse/using-stream?msg=Tell%20me%20about%20the%20world
    @GetMapping("/aws-bedrock/customerServiceResponse/using-stream")
    public Flux<String> chatOptionsBedrockResponse(@RequestParam String msg){
        /*
        NOTE: chatResponse()
        - This is the standard model response. It gives you exactly what the underlying LLM (like Bedrock/Claude) returned, plus provider-specific metadata.
        - What it contains:
           - The generated message payload (Generation objects).
           - Model-level metadata (e.g., raw token usage metrics reported by the API, finish reasons).
        - What it lacks: It is completely decoupled from the ChatClient pipeline.
           - It knows nothing about the Advisors that intercepted the request or the internal state of the framework.
        - Use case: When you only need the AI's direct output and the raw API metadata.

        NOTE: chatClientResponse()
        - Higher level wrapper record that includes ChatResponse plus the advisor Context
        - Why it matters for Advisors: When an Advisor runs, it often modifies or saves data to the shared execution context.
            - For example, a Question-Answering (RAG) Advisor will attach the specific documents it retrieved from your vector database to this context. A custom TokenUsageAdvisor might calculate billing costs and store them here.
            - You cannot access this advisor-injected data through a standard chatResponse(). You must call chatClientResponse().context()
              to retrieve it.
        - Use case: When you need visibility into what your Advisors injected, modified, or retrieved during the lifecycle of the request.
         */
        return bedrockClaudeChatClient.prompt().user(msg).stream().content();
    }


    @GetMapping("/aws-bedrock/customerServiceResponse/get-bean")
    public ResponseEntity<BooksModel> textToJavaObjects(@RequestParam String msg){
        /*
        LLM's can ouput data in different structures , depending on settings, prompt, and model.
        In this example we are using a simple prompt to get a list of books by genre.

        Prompt: Give me a list of top investing books

        NOTE: If an advisor is being used an error might occur due to formatting of the response

                StructuredOutputConverter -> BeanOutputConverter
         */
        BooksModel listOfBooks= bedrockClaudeChatClient2.prompt().user(msg).call().entity(new BeanOutputConverter<>(BooksModel.class));
        //.entity(BooksModel.class); can also be used

        return   ResponseEntity.ok(listOfBooks);
    }

    @GetMapping("/aws-bedrock/customerServiceResponse/get-list")
    public ResponseEntity<List> textToList(@RequestParam String msg){
        /*
        StructuredOutputConverter -> ListOutputConverter
        - Converts the LLM's output into a List of Strings.
        - This is useful when the LLM returns a list of items in a structured format (e.g., JSON array, bullet points).
        - The converter parses the output and extracts the individual items, returning them as a List<String> for easier processing in Java.
        - Example: If the LLM returns:
          1. Book A
          2. Book B
          3. Book C
          The ListOutputConverter will convert this into a List<String> containing ["Book A", "Book B", "Book C"].
         */
        List list= bedrockClaudeChatClient2.prompt().user(msg).call().entity(new ListOutputConverter());

        return   ResponseEntity.ok(list);
    }

    @GetMapping("/aws-bedrock/customerServiceResponse/get-map")
    public ResponseEntity<Map> textToMap(@RequestParam String msg){
        /*
        StructuredOutputConverter -> Map
        - Converts the LLM's output into a Map<String, Object>.
        - This is useful when the LLM returns key-value pairs in a structured format (e.g., JSON object, YAML).
        - The converter parses the output and extracts the key-value pairs, returning them as a Map<String, Object> for easier processing in Java.
        - Example: If the LLM returns:
          {
            "title": "Book A",
            "author": "Author A",
            "year": 2020
          }
          The MapOutputConverter will convert this into a Map<String, Object> containing {"title": "Book A", "author": "Author A", "year": 2020}.
         */
        Map map= bedrockClaudeChatClient2.prompt().user(msg).call().entity(new MapOutputConverter());

        return   ResponseEntity.ok(map);
    }


    @GetMapping("/aws-bedrock/customerServiceResponse/get-list-booksPojo")
    public ResponseEntity<List<BooksModel>> textToListPOJO(@RequestParam String msg){

        List<BooksModel> list= bedrockClaudeChatClient2.prompt().user(msg).call()
                .entity(new ParameterizedTypeReference<List<BooksModel>>() {
                });

        return   ResponseEntity.ok(list);
    }
}
