# Spring-AI Test Run 🤖

A comprehensive full-stack AI engineering project demonstrating seamless integration of multiple Large Language Models (LLMs) using the Spring AI framework. This project serves as a production-grade learning platform for Java Full Stack Engineers transitioning into AI-powered application development.

---

## 📋 Table of Contents
- [Project Vision](#project-vision)
- [Architecture Overview](#-architecture-overview)
- [Tech Stack](#️-tech-stack)
- [Project Structure](#-project-structure)
- [Core Components](#-core-components)
- [Design Patterns](#-design-patterns)
- [API Endpoints](#-api-endpoints)
- [Configuration](#-configuration)
- [Key Concepts & Features](#-key-concepts--features)
- [Getting Started](#-getting-started)
- [Learning Objectives](#-learning-objectives)

---

## Project Vision

As a **Java Fullstack Engineer** transitioning into **Full Stack AI Engineering**, this project explores and demonstrates:

- 🔄 **Multi-provider LLM Integration** - Seamless abstraction across Ollama, AWS Bedrock, OpenAI, and Docker-based models
- 🏠 **Hybrid Deployment Strategies** - Local vs. cloud-based model deployment with configurable fallbacks
- 🏗️ **Production-Ready Architecture** - Enterprise patterns including dependency injection, builder patterns, and middleware design
- 💾 **Stateful AI Applications** - Chat memory management with PostgreSQL persistence
- 📊 **Advanced Prompting Techniques** - Prompt templates, structured outputs, streaming responses
- 🎯 **AI Observability** - Token usage tracking and request/response logging via custom advisors

---

## 📸 Architecture Overview

![Project Architecture](https://github.com/LemuelBenitez/Spring-AI_Test_Run/blob/main/images/Screenshot%202026-07-11%20at%204.35.43%E2%80%AFPM.png)

![Integration Testing](https://github.com/LemuelBenitez/Spring-AI_Test_Run/blob/main/images/Screenshot%202026-07-11%20at%204.36.16%E2%80%AFPM.png)

![Database](https://github.com/LemuelBenitez/Spring-AI_Test_Run/blob/main/images/Screenshot%202026-08-16%20at%209.12.12%E2%80%AFPM.png)

### High-Level System Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                    REST API Controllers                       │
├─────────────────────────────────────────────────────────────┤
│  • ChatController          (Basic messaging)                 │
│  • PromptTemplateController (Advanced prompting)            │
│  • ChatMemoryController     (Stateful conversations)         │
│  • MultiModalController     (Provider selection)             │
├─────────────────────────────────────────────────────────────┤
│                    ChatClient Layer                          │
│  (Spring AI abstraction over LLM providers)                 │
├─────────────────────────────────────────────────────────────┤
│                  Advisor Chain (Middleware)                  │
│  • MessageChatMemoryAdvisor  (Chat persistence)             │
│  • TokenUsageAdvisor         (Observability)                │
│  • SimpleLoggerAdvisor       (Request logging)              │
├─────────────────────────────────────────────────────────────┤
│              LLM Provider Implementations                     │
│  • OllamaChatModel    (Local)                               │
│  • OpenAiChatModel    (Cloud - OpenAI)                      │
│  • BedrockProxyChatModel (Cloud - AWS)                      │
├─────────────────────────────────────────────────────────────┤
│                  Data Persistence Layer                       │
│  • JdbcChatMemoryRepository (PostgreSQL)                    │
│  • CustomChatHistoryDB      (Custom schema)                 │
└─────────────────────────────────────────────────────────────┘
```

---

## 🛠️ Tech Stack

### Framework & Core Dependencies

| Dependency | Version | Purpose |
|-----------|---------|---------|
| **Spring Boot** | 3.5.9 | Web framework and application foundation |
| **Spring AI** | 1.1.2 | AI/LLM integration and abstraction framework |
| **Java** | 25 | Latest JDK features and performance improvements |
| **PostgreSQL** | Latest | Persistent storage for chat history |
| **Maven** | 3.6+ | Build and dependency management |

### AI Model Integrations

| Provider | Integration | Use Case |
|----------|-----------|----------|
| **Ollama** | `spring-ai-starter-model-ollama` | Local private LLMs (llama, mistral) - Development & privacy-critical apps |
| **OpenAI** | `spring-ai-starter-model-openai` | GPT models via Docker or API - High-performance cloud |
| **AWS Bedrock** | `spring-ai-starter-model-bedrock-converse` | Managed Claude models - Enterprise AWS deployments |
| **Docker-based** | OpenAI-compatible API | Containerized models (Gemini) - Flexible deployments |

### Development & Documentation Tools

| Artifact | Version | Purpose |
|----------|---------|---------|
| `spring-boot-starter-web` | Latest | RESTful web service support and Spring MVC |
| `springdoc-openapi-starter-webmvc-ui` | 2.5.0 | Auto-generated Swagger/OpenAPI documentation with interactive UI |
| `spring-boot-devtools` | Latest | Hot reload and development utilities for rapid iteration |
| `spring-boot-starter-test` | Latest | JUnit 5, Mockito, AssertJ for comprehensive testing |
| `spring-boot-starter-data-jpa` | Latest | ORM and database abstraction layer |

---

## 📚 Project Structure

```
Spring-AI_Test_Run/
├── src/
│   ├── main/
│   │   ├── java/com/example/test/
│   │   │   ├── SpringAITestRunApplication.java          # Main entry point
│   │   │   ├── controller/
│   │   │   │   ├── ChatController.java                   # Basic chat endpoint
│   │   │   │   ├── PromptTemplateController.java         # Advanced prompting
│   │   │   │   ├── ChatMemoryController.java             # Stateful conversations
│   │   │   │   └── MultiModalController.java             # Multi-provider support
│   │   │   ├── config/
│   │   │   │   ├── ChatClientConfig.java                 # LLM provider configuration
│   │   │   │   └── ChatMemoryChatClientConfig.java       # Memory advisor setup
│   │   │   ├── advisor/
│   │   │   │   └── TokenUsageAdvisor.java                # Custom token tracking
│   │   │   ├── model/
│   │   │   │   └── BooksModel.java                       # Structured output model
│   │   │   └── repository/
│   │   │       └── CustomChatHistoryDB.java              # Custom schema dialect
│   │   └── resources/
│   │       ├── application.yml                           # Spring Boot config
│   │       ├── scripts.sql                               # Database initialization
│   │       └── prompts/
│   │           ├── customerSupport.st                    # Prompt templates
│   │           ├── customerSupport_PROD_Template_Example.st
│   │           └── readme_prompt.st
│   └── test/
│       └── TestApplicationTests.java                     # Integration tests
├── images/                                               # Architecture diagrams
├── pom.xml                                               # Maven configuration
└── README.md                                             # This file

```

---

## 🎯 Core Components

### 1. **Controllers** - REST API Entry Points

#### `ChatController.java`
**Purpose**: Basic LLM interaction endpoint  
**Patterns**: Dependency Injection with `@Qualifier`, Request-Response pattern

```java
@RestController
@RequestMapping("/chat-api")
public class ChatController {
    // Uses OpenAI via qualifier-based injection
    @GetMapping("/chat")
    public String chat(@RequestParam("message") String message)
```

**Endpoints**:
- `GET /chat-api/chat?message=<query>` - Send a message to OpenAI

---

#### `PromptTemplateController.java`
**Purpose**: Advanced prompting capabilities including templates, structured outputs, and streaming  
**Patterns**: Builder Pattern, Advisor Chain, Output Converters, Template Rendering

**Key Features**:
- **Prompt Templates**: External `.st` files for reusable prompts
- **Structured Output Converters**:
  - `BeanOutputConverter` - Convert LLM output to POJO
  - `ListOutputConverter` - Parse list responses
  - `MapOutputConverter` - Parse key-value pairs
  - `ParameterizedTypeReference` - Handle generic collections
- **Streaming**: Real-time response streaming via `Flux<String>`
- **Advisors**: Apply middleware for logging and token tracking

**Key Endpoints**:
- `GET /promptTemplate/default/customerServiceResponse/basic-prompt?msg=<query>`
- `GET /promptTemplate/prompt/customerServiceResponse/using-prompt-template?customerName=<name>&question=<q>`
- `GET /promptTemplate/aws-bedrock/customerServiceResponse/using-advisors?msg=<query>`
- `GET /promptTemplate/aws-bedrock/customerServiceResponse/using-stream?msg=<query>` - Streaming
- `GET /promptTemplate/aws-bedrock/customerServiceResponse/get-bean?msg=<query>` - Returns POJO
- `GET /promptTemplate/aws-bedrock/customerServiceResponse/get-list?msg=<query>` - Returns List
- `GET /promptTemplate/aws-bedrock/customerServiceResponse/get-map?msg=<query>` - Returns Map
- `GET /promptTemplate/aws-bedrock/customerServiceResponse/get-list-booksPojo?msg=<query>` - Returns List<POJO>

---

#### `ChatMemoryController.java`
**Purpose**: Stateful multi-turn conversations with persistent chat history  
**Patterns**: Advisor Pattern, Repository Pattern, Conversation ID isolation

**Key Features**:
- **Conversation Management**: Isolate conversations by conversation ID
- **Memory Types**:
  - In-Memory (default): Fast, session-scoped via `ConcurrentHashMap`
  - JDBC-backed (PostgreSQL): Persistent across restarts
- **Message Window Management**: Control how many past messages to retain
- **Multi-User Support**: Per-user conversation isolation using unique conversation IDs

**Endpoints**:
- `GET /chat-memory/chat-memory?message=<msg>` - Shared conversation (all users)
- `GET /chat-memory/chat-memory-username?username=<user>&message=<msg>` - Per-user isolated conversation

**Design Pattern Deep Dive**:
```
User Input → Advisor (MessageChatMemoryAdvisor) → Retrieves past messages
           ↓
      LLM (with context) → Response
           ↓
      Advisor stores new message → JDBC Repository → PostgreSQL
```

---

#### `MultiModalController.java`
**Purpose**: Single endpoint for testing different LLM providers  
**Patterns**: Strategy Pattern, Provider abstraction

**Endpoints**:
- `GET /api/v2/ollama?msg=<query>` - Ollama local model
- `GET /api/v2/openAI?msg=<query>` - OpenAI GPT
- `GET /api/v2/aws-bedrock?msg=<query>` - AWS Bedrock Claude

---

### 2. **Configuration** - Spring Bean Setup

#### `ChatClientConfig.java`
**Purpose**: Instantiate provider-specific ChatClient beans  
**Patterns**: Factory Pattern, Dependency Injection, Builder Pattern

**Key Concepts**:
- **Message Roles** (LLM Communication):
  - `System`: Model behavior instructions and tone
  - `User`: User's query/request/message
  - `Assistant`: Model's response/output
  - `Function/Tool`: Tool invocation directives (for function calling)

- **Provider-Specific Builders**: Each LLM provider has its own ChatClient.Builder
  - `OpenAiChatModel` → `openAiChatClient`
  - `OllamaChatModel` → `ollamaChatClient`
  - `BedrockProxyChatModel` → `bedrockClaudeChatClient`

**Bean Configuration Example**:
```java
@Bean("openAiChatClient")
public ChatClient.Builder openAiChatClient(OpenAiChatModel model) {
    return ChatClient.builder(model);
}
```

---

#### `ChatMemoryChatClientConfig.java`
**Purpose**: Configure ChatClient with memory advisors for stateful conversations  
**Patterns**: Advisor Chain Composition, Repository Pattern

**Components**:
- `JdbcChatMemoryRepository`: Stores/retrieves chat messages from PostgreSQL
- `MessageChatMemoryAdvisor`: Injects past messages into prompt context
- `CustomChatHistoryDB`: Custom SQL dialect for table schema

---

### 3. **Advisors** - Middleware for LLM Interactions

#### `TokenUsageAdvisor.java`
**Purpose**: Track and log LLM API usage (tokens, costs)  
**Patterns**: Decorator Pattern, Observer Pattern, Middleware

**Capabilities**:
- Extracts usage metadata from LLM response
- Logs token consumption (input, output, total)
- Can be extended for cost calculation and budgeting

**Advisor Chain Execution Flow**:
```
User Request
    ↓
[Advisor 1: TokenUsageAdvisor] - Track tokens pre-call
    ↓
[Advisor 2: SimpleLoggerAdvisor] - Log request/response
    ↓
[Core: ChatClient.call()] - Send to LLM
    ↓
[Advisor 2: Logging] - Log response received
    ↓
[Advisor 1: Token Tracking] - Log token usage metrics
    ↓
User Response
```

---

### 4. **Data Models**

#### `BooksModel.java`
**Purpose**: Structured output model for LLM responses  
**Pattern**: Data Transfer Object (DTO)

```java
public record BooksModel(String genre, List<String> books) { }
```

Demonstrates how Spring AI converts LLM text responses into type-safe Java objects using `BeanOutputConverter`.

---

### 5. **Repository** - Data Access Layer

#### `CustomChatHistoryDB.java`
**Purpose**: Custom PostgreSQL schema dialect for chat history  
**Pattern**: Repository Pattern, Database Abstraction, Dialect Pattern

**Customizations**:
- Custom table name: `spring_ai_chat_history` (vs. default `spring_ai_memory`)
- Custom SQL queries for insert/select/delete operations
- PostgreSQL-specific type handling (`message_type` enum)

**Database Schema**:
```sql
CREATE TABLE spring_ai_chat_history (
    id SERIAL PRIMARY KEY,
    conversation_id VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    type message_type NOT NULL,  -- Enum: USER, ASSISTANT, SYSTEM
    timestamp TIMESTAMP DEFAULT NOW()
);
```

---

## 🏗️ Design Patterns Used

### 1. **Adapter/Bridge Pattern** - Multi-LLM Abstraction
Spring AI's `ChatClient` abstracts provider-specific implementations:
- Same API regardless of underlying LLM (Ollama, OpenAI, Bedrock)
- Easy switching between providers via bean qualifiers
- Vendor lock-in prevention

### 2. **Builder Pattern** - Fluent API
```java
chatClient.prompt()
    .user(message)
    .advisors(List.of(new TokenUsageAdvisor()))
    .call()
    .content()
```

### 3. **Strategy Pattern** - Provider Selection
```java
// In MultiModalController: Different strategies per endpoint
@GetMapping("/ollama") → ollamaChatClient
@GetMapping("/openAI") → openAiChatClient
@GetMapping("/aws-bedrock") → bedrockClaudeChatClient
```

### 4. **Advisor/Middleware Pattern** - Cross-Cutting Concerns
```
Request → [Advisor Chain] → LLM → [Advisor Chain] → Response
```
Used for:
- Chat memory injection
- Token tracking and billing
- Request/response logging
- Security/safety checks (SafeGuardAdvisors)

### 5. **Repository Pattern** - Data Access Abstraction
```java
JdbcChatMemoryRepository
    ↓
CustomChatHistoryDB (SQL generation)
    ↓
PostgreSQL
```

### 6. **Dependency Injection with Qualifiers** - Runtime Provider Selection
```java
public ChatController(@Qualifier("openAiChatClient") ChatClient.Builder chat)
```

### 7. **Converter Pattern** - Output Type Transformation
- `BeanOutputConverter<T>` - LLM text → Java POJO
- `ListOutputConverter` - LLM list → Java List
- `MapOutputConverter` - LLM object → Java Map
- `ParameterizedTypeReference` - Generic type handling

### 8. **Factory Pattern** - Bean Creation
Spring's `@Configuration` and `@Bean` annotations create provider-specific ChatClient instances

---

## 📡 API Endpoints Quick Reference

| Endpoint | Method | Purpose | Provider |
|----------|--------|---------|----------|
| `/chat-api/chat` | GET | Basic chat | OpenAI |
| `/promptTemplate/default/**` | GET | Simple prompting | Ollama |
| `/promptTemplate/prompt/**` | GET | Template-based prompting | OpenAI |
| `/promptTemplate/aws-bedrock/**` | GET | Advanced features (streaming, advisors) | Bedrock |
| `/chat-memory/chat-memory` | GET | Stateful chat (shared) | OpenAI |
| `/chat-memory/chat-memory-username` | GET | Stateful chat (per-user) | OpenAI |
| `/api/v2/ollama` | GET | Local LLM | Ollama |
| `/api/v2/openAI` | GET | Cloud LLM | OpenAI |
| `/api/v2/aws-bedrock` | GET | Managed LLM | Bedrock |

### Example Usage

```bash
# Basic chat
curl "http://localhost:8080/chat-api/chat?message=Hello"

# Using prompt templates
curl "http://localhost:8080/promptTemplate/prompt/customerServiceResponse/using-prompt-template?customerName=John&question=How%20do%20I%20reset%20my%20password"

# Stateful conversation
curl "http://localhost:8080/chat-memory/chat-memory?message=What%20is%20my%20name"

# Get structured output (POJO)
curl "http://localhost:8080/promptTemplate/aws-bedrock/customerServiceResponse/get-bean?msg=Give%20me%20top%205%20business%20books"

# Multi-provider testing
curl "http://localhost:8080/api/v2/ollama?msg=Explain%20machine%20learning"
```

---

## ⚙️ Configuration

### `application.yml` Overview

#### Database Connection
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/gen-ai-memory-db
    username: admin
    password: admin
    driver-class-name: org.postgresql.Driver
```

#### LLM Provider Configuration

**OpenAI (with Docker fallback)**:
```yaml
spring.ai.openai:
  chat.options.model: ai/gemma3
  api-key: dummy  # For Docker-based model
  base-url: http://localhost:12434/engines  # Docker model runner
```

**Ollama (Local)**:
```yaml
spring.ai.ollama:
  chat.options.model: llama3.2:1b
  # Default: http://localhost:11434
```

**AWS Bedrock (Enterprise)**:
```yaml
spring.ai.bedrock:
  aws:
    access-key: ${AWS_ACCESS_KEY_ID}
    secret-key: ${AWS_SECRET_ACCESS_KEY}
    region: us-east-1
  converse:
    chat.options.model: arn:aws:bedrock:us-east-1:...
```

#### Chat Memory Configuration
```yaml
spring.ai.chat.memory.repository.jdbc:
  initialize-schema: never  # Custom schema via scripts.sql
  schema: classpath:/scripts.sql
```

---

## 🔑 Key Concepts & Features

### 1. **Prompt Templates**
External `.st` (StringTemplate) files for reusable, maintainable prompts:

```yaml
@Value("classpath:prompts/customerSupport_PROD_Template_Example.st")
private Resource promptTemplateResource;

// Usage:
chatClient.prompt()
    .user(promptTemplateResource ->
        promptTemplateResource.param("customerName", name)
                             .param("question", question))
    .call().content()
```

### 2. **Prompt Stuffing vs. RAG**
- **Prompt Stuffing**: Include all context in prompt (token limit issues)
- **RAG (Retrieval-Augmented Generation)**: Retrieve relevant documents dynamically (scalable)
- This project demonstrates prompt stuffing basics; RAG can be added via vector databases

### 3. **Structured Outputs**
Convert LLM text responses into type-safe Java objects:

```java
// Input: "Top books: 1. Atomic Habits 2. Deep Work"
// Output: List<String> ["Atomic Habits", "Deep Work"]
List<String> books = chatClient.prompt()
    .user(prompt)
    .call()
    .entity(new ListOutputConverter());
```

### 4. **Chat Memory Isolation**
Prevent conversation cross-talk in multi-user scenarios:

```java
// Default: All users share same memory (not recommended for production)
advisorSpec.param(ChatMemory.CONVERSATION_ID, "default")

// Per-user: Each user has isolated conversation
advisorSpec.param(ChatMemory.CONVERSATION_ID, username)
```

### 5. **Streaming Responses**
Real-time LLM output via reactive streams (Project Reactor Flux):

```java
@GetMapping("/stream")
public Flux<String> streamResponse(@RequestParam String msg) {
    return chatClient.prompt()
        .user(msg)
        .stream()
        .content();  // Flux<String> for real-time token-by-token delivery
}
```

### 6. **Default System Prompts**
Set model behavior globally:

```java
chatClient = ChatClient.builder(model)
    .defaultSystem("You are a helpful customer support agent...")
    .defaultUser("How can you help?")
    .build();
```

### 7. **Token Usage Tracking**
Monitor and optimize API costs:

```java
public ChatClientResponse adviseCall(ChatClientRequest req, CallAdvisorChain chain) {
    ChatClientResponse resp = chain.nextCall(req);
    Usage usage = resp.chatResponse().getMetadata().getUsage();
    // Log: input_tokens, output_tokens, total_tokens
    logger.info(usage.toString());
    return resp;
}
```

---

## 🚀 Getting Started

### Prerequisites
- **Java 25+**: [Download JDK 25](https://www.oracle.com/java/technologies/downloads/)
- **Maven 3.6+**: `mvn -v`
- **Docker**: For Ollama or Docker-based models
- **PostgreSQL 12+**: For chat memory persistence
- **API Keys** (optional, based on provider):
  - OpenAI: `OPENAI_API_KEY`
  - AWS: `AWS_ACCESS_KEY_ID`, `AWS_SECRET_ACCESS_KEY`

### Setup & Run

```bash
# 1. Clone repository
git clone https://github.com/LemuelBenitez/Spring-AI_Test_Run.git
cd Spring-AI_Test_Run

# 2. Start PostgreSQL (if using chat memory)
docker run -d \
  --name postgres-ai \
  -e POSTGRES_PASSWORD=admin \
  -e POSTGRES_USER=admin \
  -e POSTGRES_DB=gen-ai-memory-db \
  -p 5432:5432 \
  postgres:16

# 3. Start Ollama (if using local models)
# Follow: https://ollama.ai (macOS/Linux/Windows)
# Then: ollama run llama3.2:1b

# 4. Set environment variables
export OPENAI_API_KEY=sk-...  # if using OpenAI
export AWS_ACCESS_KEY_ID=...   # if using Bedrock
export AWS_SECRET_ACCESS_KEY=...

# 5. Build
mvn clean package

# 6. Run
mvn spring-boot:run
# Or: java -jar target/test-0.0.1-SNAPSHOT.jar

# 7. Access API
# Swagger UI: http://localhost:8080/swagger-ui.html
# Basic endpoint: http://localhost:8080/api/v2/ollama?msg=Hello
```

### Docker Compose (Optional - Recommended for Local Setup)

```yaml
version: '3.8'
services:
  postgres:
    image: postgres:16
    environment:
      POSTGRES_USER: admin
      POSTGRES_PASSWORD: admin
      POSTGRES_DB: gen-ai-memory-db
    ports:
      - "5432:5432"
    volumes:
      - postgres_data:/var/lib/postgresql/data

  ollama:
    image: ollama/ollama:latest
    ports:
      - "11434:11434"
    volumes:
      - ollama_data:/root/.ollama

volumes:
  postgres_data:
  ollama_data:
```

---

## 🎓 Learning Objectives

This project is designed for Java Full Stack Engineers learning AI fundamentals:

### Core Concepts Covered
- ✅ **LLM Integration**: Abstraction over provider differences
- ✅ **REST API Design**: Clean, scalable endpoints for AI services
- ✅ **Stateful Systems**: Managing conversation context and memory
- ✅ **Middleware/Advisors**: Cross-cutting concerns in AI pipelines
- ✅ **Structured Outputs**: Type-safe LLM response handling
- ✅ **Database Persistence**: Long-term conversation storage
- ✅ **Configuration Management**: Multi-environment setup (local/cloud)
- ✅ **Observability**: Token tracking and cost monitoring
- ✅ **Prompt Engineering**: Templates, stuffing, and role-based messaging

### Advanced Topics to Explore
- 🔜 **Retrieval-Augmented Generation (RAG)**: Vector databases + embeddings
- 🔜 **Function Calling**: Tools and agent frameworks
- 🔜 **Fine-tuning**: Custom model adaptation
- 🔜 **Multi-Agent Systems**: Orchestrating multiple LLMs
- 🔜 **Streaming & Real-Time**: WebSocket support for live responses
- 🔜 **Security**: Prompt injection prevention, API key management

---

## 📸 Database Schema

The project uses PostgreSQL with a custom schema for chat history storage:

```sql
CREATE TABLE spring_ai_chat_history (
    id SERIAL PRIMARY KEY,
    conversation_id VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    type message_type NOT NULL,  -- USER, ASSISTANT, SYSTEM
    timestamp TIMESTAMP DEFAULT NOW()
);

CREATE INDEX idx_conversation_id ON spring_ai_chat_history(conversation_id);
CREATE INDEX idx_timestamp ON spring_ai_chat_history(timestamp);
```

---

## 🔒 Security Considerations

- ✅ Environment variable management for sensitive API keys
- ⚠️ Add API rate limiting for production
- ⚠️ Implement authentication/authorization for multi-user scenarios
- ⚠️ Validate/sanitize user inputs before sending to LLMs
- ⚠️ Consider prompt injection attack prevention
- ⚠️ Use HTTPS for all API communications
- ⚠️ Implement request signing for AWS Bedrock

---

## 🔑 Key Features Summary

✅ **Multi-LLM Integration** - Seamless switching between Ollama, OpenAI, AWS Bedrock  
✅ **Spring Boot REST API** - Production-ready endpoints with auto-validation  
✅ **Swagger/OpenAPI** - Auto-generated, interactive API documentation  
✅ **Local & Cloud Deployment** - Flexibility in deployment strategy  
✅ **Chat Memory** - PostgreSQL-backed persistent conversations  
✅ **Streaming Responses** - Real-time token-by-token output  
✅ **Structured Outputs** - Type-safe POJO/List/Map conversions  
✅ **Prompt Templates** - External `.st` files for reusable prompts  
✅ **Token Tracking** - Built-in observability for cost monitoring  
✅ **Dev Tools** - Hot reload, debugging, and rapid iteration  

---

## 📝 License

This is an educational learning project for skill development in AI engineering. See the repository for license details.

---

## 🙏 Acknowledgments

- **Spring AI Framework**: Excellent abstraction over LLM providers
- **LLM Providers**: Ollama, OpenAI, AWS Bedrock for making AI accessible
- **Spring Community**: For production-grade patterns and best practices

---

**Developed by:** Lemuel Benitez  
**Role:** Java Fullstack Engineer → Full Stack AI Engineer (In Progress) 🚀  
**Last Updated:** August 16, 2026

---

### Quick Links
- [Spring AI Documentation](https://docs.spring.io/spring-ai/reference/)
- [OpenAI API Reference](https://platform.openai.com/docs/api-reference)
- [AWS Bedrock Documentation](https://docs.aws.amazon.com/bedrock/)
- [Ollama Documentation](https://github.com/ollama/ollama)
