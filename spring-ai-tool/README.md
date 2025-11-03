# Spring AI Tool - AI Agent with Function Calling

A Spring Boot application that demonstrates AI agent capabilities using Spring AI with OpenAI's function calling feature. The agent can automatically use Weather and Wikipedia tools to answer user queries.

## Features

- **AI Agent with Function Calling**: Automatically determines which tools to use based on user queries
- **Weather Tool**: Get real-time weather information for any location
- **Wikipedia Tool**: Fetch article summaries from Wikipedia
- **REST API**: Easy-to-use HTTP endpoints for integration

## Prerequisites

- Java 21+
- Maven 3.6+
- OpenAI API Key

## Setup

1. **Set your OpenAI API Key**

   Option 1: Environment Variable (Recommended)
   ```bash
   export OPENAI_API_KEY=your-api-key-here
   ```

   Option 2: Update `application.yml`
   ```yaml
   spring:
     ai:
       openai:
         api-key: your-api-key-here
   ```

2. **Build the project**
   ```bash
   mvn clean install
   ```

3. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

   The application will start on `http://localhost:8080`

## API Endpoints

### 1. AI Agent Chat (Automatic Tool Selection)

The AI agent automatically decides which tools to use based on the user's message.

**Endpoint:** `POST /api/agent/chat`

**Request Body:**
```json
{
  "message": "What's the weather in London and tell me about Big Ben?"
}
```

**Response:**
```json
{
  "response": "The weather in London is currently 15°C with partly cloudy conditions. Big Ben is the nickname for the Great Bell of the clock at the north end of the Palace of Westminster in London...",
  "usedTools": true,
  "toolsUsed": "Weather and Wikipedia tools available"
}
```

**Example Queries:**
- "What's the weather in Tokyo?"
- "Tell me about Albert Einstein"
- "What's the temperature in Paris and give me information about the Eiffel Tower"
- "Is it raining in New York?"

### 2. Direct Weather Tool

Bypass the AI and call the weather tool directly.

**Endpoint:** `GET /api/agent/weather?location={location}`

**Example:**
```bash
curl "http://localhost:8080/api/agent/weather?location=London"
```

**Response:**
```
Weather for London:
- Condition: Partly cloudy
- Temperature: 15°C (Feels like: 13°C)
- Today's Range: 12°C to 18°C
- Humidity: 65%
- Wind: 15 km/h NW
- Precipitation: 0 mm
- UV Index: 3
```

### 3. Direct Wikipedia Tool

Bypass the AI and call the Wikipedia tool directly.

**Endpoint:** `GET /api/agent/wikipedia?topic={topic}`

**Example:**
```bash
curl "http://localhost:8080/api/agent/wikipedia?topic=Spring%20Framework"
```

**Response:**
```json
{
  "type": "standard",
  "title": "Spring Framework",
  "extract": "The Spring Framework is an application framework...",
  ...
}
```

### 4. Health Check

**Endpoint:** `GET /api/agent/health`

**Response:**
```
AI Agent is running with Weather and Wikipedia tools enabled
```

## Testing with cURL

### Chat with AI Agent
```bash
curl -X POST http://localhost:8080/api/agent/chat \
  -H "Content-Type: application/json" \
  -d '{"message": "What is the weather in Paris?"}'
```

### Get Weather
```bash
curl "http://localhost:8080/api/agent/weather?location=Tokyo"
```

### Get Wikipedia Article
```bash
curl "http://localhost:8080/api/agent/wikipedia?topic=Artificial%20Intelligence"
```

## How It Works

1. **User sends a message** to the `/api/agent/chat` endpoint
2. **AI Agent analyzes** the message to determine intent
3. **Function calling** is triggered if tools are needed:
   - Weather queries → `WeatherTool.getWeather()`
   - Information queries → `WikipediaTool.getArticle()`
4. **Tools execute** and return results to the AI
5. **AI synthesizes** the tool results into a natural language response
6. **Response sent** back to the user

## Architecture

```
┌─────────────────┐
│  REST Client    │
└────────┬────────┘
         │
         ▼
┌─────────────────────┐
│  AIAgentController  │
└────────┬────────────┘
         │
         ▼
┌─────────────────────┐
│  AIAgentService     │
│  (ChatClient)       │
└────────┬────────────┘
         │
    ┌────┴────┐
    ▼         ▼
┌─────────┐ ┌──────────────┐
│ Weather │ │  Wikipedia   │
│  Tool   │ │    Tool      │
└─────────┘ └──────────────┘
```

## Configuration

### application.yml
```yaml
spring:
  application:
    name: spring-ai-tool
  ai:
    openai:
      api-key: ${OPENAI_API_KEY:your-api-key-here}
      chat:
        options:
          model: gpt-4o-mini
          temperature: 0.7

server:
  port: 8080
```

## Technologies Used

- **Spring Boot 3.5.7**
- **Spring AI 1.0.3**
- **OpenAI GPT-4o-mini**
- **Java 21**
- **Maven**

## Author

@avinash
