# Spring-AI Test Run 🤖

A comprehensive full-stack AI engineering project demonstrating integration of multiple Large Language Models (LLMs) using Spring AI framework. This project serves as a learning platform for expanding Java fullstack development skills into AI fundamentals.

---

## Project Vision

As a **Java Fullstack Engineer** transitioning into **Full Stack AI Engineering**, this project explores and demonstrates:
- Integration with multiple LLM providers via Spring AI
- Local vs. cloud-based AI model deployment
- Building AI-powered REST APIs with Spring Boot
- Production-ready patterns for AI applications

---

## 📸 Project Overview

![Project Architecture](images/Screenshot%202026-07-11%20at%204.35.43%20PM.png)

![Integration Testing](images/Screenshot%202026-07-11%20at%204.36.16%20PM.png)

---

## 🎯 Project Purpose

This project was created to test and demonstrate connecting to multiple LLMs via Spring-AI framework:

### Supported LLM Providers

1. **Ollama** - Local Private LLM
   - Run LLMs locally without cloud dependencies
   - Complete data privacy
   - Perfect for development and testing

2. **AWS Bedrock** - Cloud-Hosted LLM Service
   - Managed LLM service by Amazon
   - Scalable enterprise-grade deployment
   - Integration with AWS ecosystem

3. **OpenAI** - Premium Cloud LLM Service
   - GPT-series models (state-of-the-art performance)
   - RESTful API integration
   - Enterprise-grade reliability

4. **Gemini via Docker** - Google's Lightweight LLM
   - Containerized deployment using Docker
   - Efficient resource usage
   - Flexible deployment options

---

## 🛠️ Tech Stack

### Framework & Core Dependencies

| Dependency | Version | Purpose |
|-----------|---------|---------|
| **Spring Boot** | 3.5.9 | Web framework and application foundation |
| **Spring AI** | 1.1.2 | AI/LLM integration framework |
| **Java** | 25 | Programming language (latest version) |

### AI Model Integrations

| Artifact | Purpose |
|----------|---------|
| `spring-ai-starter-model-bedrock-converse` | AWS Bedrock LLM provider |
| `spring-ai-starter-model-ollama` | Ollama local LLM provider |
| `spring-ai-starter-model-openai` | OpenAI GPT models provider |

### Development Tools

| Artifact | Version | Purpose |
|----------|---------|---------|
| `spring-boot-starter-web` | Latest | RESTful web service support |
| `springdoc-openapi-starter-webmvc-ui` | 2.5.0 | Swagger/OpenAPI documentation UI |
| `spring-boot-devtools` | Latest | Hot reload and development utilities |
| `spring-boot-starter-test` | Latest | Testing framework (JUnit, Mockito, etc.) |

---

## 🚀 Getting Started

### Prerequisites
- Java 25 or higher
- Maven 3.6+
- Docker (for local LLM deployment)
- One or more of: Ollama, AWS credentials, OpenAI API key

### Build & Run

```bash
# Build the project
mvn clean package

# Run the application
mvn spring-boot:run
```

### Access Swagger UI
Once running, visit: `http://localhost:8080/swagger-ui.html`

---

## 📚 Project Structure

```
Spring-AI_Test_Run/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/test/
│   │   │       └── [Controller & Service Classes]
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── [Unit & Integration Tests]
├── images/
│   └── [Project screenshots]
├── pom.xml
└── README.md
```

---

## 🔑 Key Features

✅ **Multi-LLM Integration** - Seamless switching between different LLM providers  
✅ **Spring Boot REST API** - Clean, production-ready API endpoints  
✅ **Swagger Documentation** - Auto-generated API documentation  
✅ **Local & Cloud Deployment** - Flexibility in deployment strategy  
✅ **Development Tools** - Hot reload for rapid iteration  

---

## 🎓 Learning Objectives

This project demonstrates:
- How to integrate AI services into traditional Java fullstack applications
- Understanding LLM provider APIs and their strengths/weaknesses
- Building scalable AI-powered microservices
- Best practices for AI application development
- DevOps considerations for AI workloads

---

## 📝 License

This is a learning project. See the repository for license details.

---

**Developed by:** Lemuel Benitez  
**Role:** Java Fullstack Engineer → Full Stack AI Engineer (In Progress) 🚀
