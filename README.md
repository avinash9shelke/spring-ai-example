# Spring AI Example with OpenAI GPT-5-nano

A comprehensive Spring Boot application demonstrating integration with OpenAI's GPT-5-nano model using Spring AI.

## Features

- **Simple Chat**: Basic chat completion with GPT-5-nano
- **Custom Options**: Chat with custom temperature and max tokens
- **Streaming Responses**: Real-time streaming chat using Server-Sent Events
- **System Prompts**: Chat with custom system prompts for role-based responses
- **Multi-turn Conversations**: Handle conversation history
- **RESTful API**: Clean REST endpoints for all operations

## Prerequisites

- Java 21 or higher
- Maven 3.6+
- OpenAI API Key

## Setup

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd spring-ai-example
   ```

2. **Set your OpenAI API Key**
   
   Export the API key as an environment variable:
   ```bash
   export OPENAI_API_KEY=your-api-key-here
   ```
   
   Or create an `.env` file in the project root:
   ```
   OPENAI_API_KEY=your-api-key-here
   ```

3. **Build the project**
   ```bash
   cd ai-example
   ./mvnw clean install
   ```

4. **Run the application**
   ```bash
   ./mvnw spring-boot:run
   ```

The application will start on `http://localhost:8080`

## API Endpoints

### 1. Health Check
```bash
curl http://localhost:8080/api/chat/health
```

### 2. Simple Chat
```bash
curl -X POST http://localhost:8080/api/chat/simple \
  -H "Content-Type: application/json" \
  -d '{"message": "What is Spring Boot?"}'
```

### 3. Chat with Custom Options
```bash
curl -X POST http://localhost:8080/api/chat/custom \
  -H "Content-Type: application/json" \
  -d '{
    "message": "Explain quantum computing in simple terms",
    "temperature": 0.8,
    "maxTokens": 500
  }'
```

### 4. Streaming Chat
```bash
curl -X POST http://localhost:8080/api/chat/stream \
  -H "Content-Type: application/json" \
  -d '{"message": "Write a short story about AI"}' \
  --no-buffer
```

### 5. Chat with System Prompt
```bash
curl -X POST http://localhost:8080/api/chat/system-prompt \
  -H "Content-Type: application/json" \
  -d '{
    "message": "How do I improve my code quality?",
    "systemPrompt": "You are an expert software engineer specializing in clean code practices."
  }'
```

### 6. Multi-turn Conversation
```bash
curl -X POST http://localhost:8080/api/chat/conversation \
  -H "Content-Type: application/json" \
  -d '{
    "messages": [
      "Hello, I want to learn about microservices",
      "What are the main benefits?",
      "Can you give me an example?"
    ]
  }'
```

## Configuration
Edit `src/main/resources/application.yml` to customize:

```yaml
spring:
  ai:
    openai:
      api-key: ${OPENAI_API_KEY}
      chat:
        options:
          model: gpt-5-nano
          temperature: 0.7
          max-tokens: 1000

server:
  port: 8080
```

## Project Structure

```
ai-example/
├── src/main/java/com/ai/example/
│   ├── Application.java              # Main application class
│   ├── controller/
│   │   └── OpenAIController.java     # REST API endpoints
│   ├── service/
│   │   └── OpenAiService.java        # AI service layer
│   └── dto/
│       ├── ChatRequest.java          # Request DTO
│       ├── ChatResponse.java         # Response DTO
│       └── StreamChatRequest.java    # Streaming request DTO
├── src/main/resources/
│   └── application.yml               # Application configuration
└── pom.xml                           # Maven dependencies
```

## Dependencies

- **Spring Boot 3.5.7**: Core framework
- **Spring AI 1.0.3**: AI integration framework
- **Spring AI Starter Model OpenAI**: OpenAI model integration
- **Spring Web**: REST API support

## Usage Examples

### Example 1: Code Review Assistant
```bash
curl -X POST http://localhost:8080/api/chat/system-prompt \
  -H "Content-Type: application/json" \
  -d '{
    "message": "Review this code: public void process() { String s = null; s.length(); }",
    "systemPrompt": "You are a code review expert. Identify bugs and suggest improvements."
  }'
```

### Example 2: Creative Writing
```bash
curl -X POST http://localhost:8080/api/chat/custom \
  -H "Content-Type: application/json" \
  -d '{
    "message": "Write a haiku about programming",
    "temperature": 1.0,
    "maxTokens": 100
  }'
```

### Example 3: Technical Documentation
```bash
curl -X POST http://localhost:8080/api/chat/simple \
  -H "Content-Type: application/json" \
  -d '{"message": "Explain REST API best practices"}'
```

## Notes

- The application uses GPT-5-nano model as configured in `application.yml`
- Temperature controls randomness (0.0 = deterministic, 1.0 = creative)
- Max tokens limits the response length
- Streaming endpoint returns Server-Sent Events for real-time responses
- All endpoints return JSON except the streaming endpoint

## Troubleshooting

**Issue**: `401 Unauthorized`
- **Solution**: Verify your `OPENAI_API_KEY` is set correctly

**Issue**: `Model not found`
- **Solution**: Ensure GPT-5-nano is available in your OpenAI account or change to an available model (e.g., gpt-4, gpt-3.5-turbo)

**Issue**: Connection timeout
- **Solution**: Check your internet connection and OpenAI API status

## License

MIT License
