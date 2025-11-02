# Spring AI Audio Application with GPT-4o-Audio-Preview

A comprehensive Spring Boot application demonstrating integration with OpenAI's **gpt-4o-audio-preview** model using Spring AI. This application provides REST endpoints for audio-enabled AI interactions.

## Features

- **Simple Chat**: Basic chat completion with gpt-4o-audio-preview
- **Audio-Enabled Chat**: Chat with audio output capabilities
- **Custom Options**: Chat with custom temperature and max tokens
- **Streaming Responses**: Real-time streaming chat using Server-Sent Events
- **System Prompts**: Chat with custom system prompts for role-based responses
- **Multi-turn Conversations**: Handle conversation history
- **Voice Selection**: Support for multiple voice options (alloy, echo, fable, onyx, nova, shimmer)
- **Audio Format Support**: Multiple audio formats (mp3, wav, pcm16, g711_ulaw, g711_alaw)
- **RESTful API**: Clean REST endpoints for all operations

## Prerequisites

- Java 21 or higher
- Maven 3.6+
- OpenAI API Key with access to gpt-4o-audio-preview model

## Setup

1. **Navigate to the audio-ai directory**
   ```bash
   cd audio-ai
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
   ./mvnw clean install
   ```

4. **Run the application**
   ```bash
   ./mvnw spring-boot:run
   ```

The application will start on `http://localhost:8081`

## API Endpoints

### 1. Health Check
```bash
curl http://localhost:8081/api/audio/health
```

**Response:**
```json
{
  "status": "UP",
  "service": "audio-ai",
  "model": "gpt-4o-audio-preview"
}
```

### 2. Simple Chat
```bash
curl -X POST http://localhost:8081/api/audio/chat/simple \
  -H "Content-Type: application/json" \
  -d '{"message": "What is Spring Boot?"}'
```

**Response:**
```json
{
  "response": "Spring Boot is a framework...",
  "model": "gpt-4o-audio-preview"
}
```

### 3. Chat with Audio Output
```bash
curl -X POST http://localhost:8081/api/audio/chat/audio \
  -H "Content-Type: application/json" \
  -d '{
    "message": "Explain quantum computing",
    "audioFormat": "mp3",
    "voice": "alloy"
  }'
```

**Response:**
```json
{
  "textResponse": "Quantum computing is...",
  "audioBase64": null,
  "audioFormat": "mp3",
  "model": "gpt-4o-audio-preview",
  "timestamp": 1234567890
}
```

### 4. Chat with Custom Options
```bash
curl -X POST http://localhost:8081/api/audio/chat/custom \
  -H "Content-Type: application/json" \
  -d '{
    "message": "Explain quantum computing in simple terms",
    "temperature": 0.8,
    "maxTokens": 500
  }'
```

**Response:**
```json
{
  "response": "Quantum computing...",
  "model": "gpt-4o-audio-preview",
  "temperature": 0.8,
  "maxTokens": 500
}
```

### 5. Streaming Chat
```bash
curl -X POST http://localhost:8081/api/audio/chat/stream \
  -H "Content-Type: application/json" \
  -d '{"message": "Write a short story about AI"}' \
  --no-buffer
```

**Response:** Server-Sent Events stream

### 6. Chat with System Prompt
```bash
curl -X POST http://localhost:8081/api/audio/chat/system-prompt \
  -H "Content-Type: application/json" \
  -d '{
    "message": "How do I improve my code quality?",
    "systemPrompt": "You are an expert software engineer specializing in clean code practices."
  }'
```

**Response:**
```json
{
  "response": "To improve code quality...",
  "model": "gpt-4o-audio-preview"
}
```

### 7. Multi-turn Conversation
```bash
curl -X POST http://localhost:8081/api/audio/chat/conversation \
  -H "Content-Type: application/json" \
  -d '{
    "messages": [
      "Hello, I want to learn about microservices",
      "What are the main benefits?",
      "Can you give me an example?"
    ]
  }'
```

**Response:**
```json
{
  "response": "Based on your questions...",
  "model": "gpt-4o-audio-preview",
  "messageCount": 3
}
```

### 8. Get Available Voices
```bash
curl http://localhost:8081/api/audio/voices
```

**Response:**
```json
{
  "voices": ["alloy", "echo", "fable", "onyx", "nova", "shimmer"],
  "defaultVoice": "alloy"
}
```

### 9. Get Supported Audio Formats
```bash
curl http://localhost:8081/api/audio/formats
```

**Response:**
```json
{
  "formats": ["mp3", "wav", "pcm16", "g711_ulaw", "g711_alaw"],
  "defaultFormat": "mp3"
}
```

## Configuration

Edit `src/main/resources/application.yml` to customize:

```yaml
spring:
  application:
    name: audio-ai
  ai:
    openai:
      api-key: ${OPENAI_API_KEY}
      chat:
        options:
          model: gpt-4o-audio-preview
          temperature: 0.7
          max-tokens: 2000

server:
  port: 8081
```

## Project Structure

```
audio-ai/
├── src/main/java/com/ai/audio/
│   ├── AudioAiApplication.java          # Main application class
│   ├── controller/
│   │   └── AudioController.java         # REST API endpoints
│   ├── service/
│   │   └── AudioService.java            # AI service layer
│   └── dto/
│       ├── AudioChatRequest.java        # Audio chat request DTO
│       ├── AudioChatResponse.java       # Audio chat response DTO
│       ├── SimpleTextRequest.java       # Simple text request DTO
│       ├── TextToAudioRequest.java      # Text-to-audio request DTO
│       └── AudioTranscriptionRequest.java # Audio transcription DTO
├── src/main/resources/
│   └── application.yml                  # Application configuration
└── pom.xml                              # Maven dependencies
```

## Dependencies

- **Spring Boot 3.5.7**: Core framework
- **Spring AI 1.0.3**: AI integration framework
- **Spring AI Starter Model OpenAI**: OpenAI model integration
- **Spring Web**: REST API support

## Voice Options

The gpt-4o-audio-preview model supports multiple voices:

- **alloy**: Neutral and balanced (default)
- **echo**: Clear and articulate
- **fable**: Warm and expressive
- **onyx**: Deep and authoritative
- **nova**: Energetic and friendly
- **shimmer**: Soft and gentle

## Audio Formats

Supported audio formats:

- **mp3**: MPEG Audio Layer III (default)
- **wav**: Waveform Audio File Format
- **pcm16**: 16-bit PCM
- **g711_ulaw**: G.711 μ-law
- **g711_alaw**: G.711 A-law

## Usage Examples

### Example 1: Technical Documentation with Audio
```bash
curl -X POST http://localhost:8081/api/audio/chat/audio \
  -H "Content-Type: application/json" \
  -d '{
    "message": "Explain REST API best practices",
    "voice": "onyx",
    "audioFormat": "mp3"
  }'
```

### Example 2: Creative Writing
```bash
curl -X POST http://localhost:8081/api/audio/chat/custom \
  -H "Content-Type: application/json" \
  -d '{
    "message": "Write a haiku about programming",
    "temperature": 1.0,
    "maxTokens": 100
  }'
```

### Example 3: Code Review Assistant
```bash
curl -X POST http://localhost:8081/api/audio/chat/system-prompt \
  -H "Content-Type: application/json" \
  -d '{
    "message": "Review this code: public void process() { String s = null; s.length(); }",
    "systemPrompt": "You are a code review expert. Identify bugs and suggest improvements."
  }'
```

## Notes

- The application uses **gpt-4o-audio-preview** model with audio capabilities
- Temperature controls randomness (0.0 = deterministic, 1.0 = creative)
- Max tokens limits the response length
- Streaming endpoint returns Server-Sent Events for real-time responses
- Audio output is supported through the model's native audio capabilities
- All endpoints return JSON except the streaming endpoint (SSE)

## Model Capabilities

The **gpt-4o-audio-preview** model provides:

- **Text Understanding**: Advanced natural language processing
- **Audio Generation**: Native audio output capabilities
- **Multi-modal Support**: Handle both text and audio modalities
- **High Quality**: State-of-the-art language understanding
- **Low Latency**: Optimized for real-time interactions

## Troubleshooting

**Issue**: `401 Unauthorized`
- **Solution**: Verify your `OPENAI_API_KEY` is set correctly and has access to gpt-4o-audio-preview

**Issue**: `Model not found`
- **Solution**: Ensure gpt-4o-audio-preview is available in your OpenAI account. This is a preview model and may require special access.

**Issue**: Connection timeout
- **Solution**: Check your internet connection and OpenAI API status

**Issue**: Port already in use
- **Solution**: The application runs on port 8081. Change the port in `application.yml` if needed.

## Development

To run in development mode with auto-reload:

```bash
./mvnw spring-boot:run -Dspring-boot.run.jvmArguments="-Dspring.devtools.restart.enabled=true"
```

## Testing

Run the tests:

```bash
./mvnw test
```

## License

MIT License
