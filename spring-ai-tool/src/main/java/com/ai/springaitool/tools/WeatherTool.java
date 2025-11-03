package com.ai.springaitool.tools;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

/**
 * @author avinash
 */
@Service
@Slf4j
public class WeatherTool {

    private static final String WEATHER_API_URL = "https://wttr.in/%s?format=j1";
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Get current weather information for a given location.
     *
     * @param location The city name or location (e.g., "London", "New York", "Tokyo")
     * @return Weather information including temperature, conditions, humidity, and wind speed
     */
    @Tool(name = "getWeather", description = "Get current weather information for a specific location. Provide the city name or location.")
    public String getWeather(String location) {
        log.info("Fetching weather for '{}'", location);
        if (location == null || location.trim().isEmpty()) {
            return "Error: Location cannot be empty. Please provide a valid city name.";
        }

        try {
            String encodedLocation = URLEncoder.encode(location.trim(), StandardCharsets.UTF_8);
            String url = String.format(WEATHER_API_URL, encodedLocation);

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("User-Agent", "Spring-AI-Weather-Tool")
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return parseWeatherResponse(response.body(), location);
            } else {
                return String.format("Failed to fetch weather data for '%s'. Status code: %d", location, response.statusCode());
            }

        } catch (Exception e) {
            return String.format("Error fetching weather for '%s': %s", location, e.getMessage());
        }
    }

    /**
     * Parse the weather API response and format it into a readable string.
     */
    private String parseWeatherResponse(String jsonResponse, String location) {
        try {
            JsonNode root = objectMapper.readTree(jsonResponse);
            JsonNode currentCondition = root.path("current_condition").get(0);
            JsonNode weather = root.path("weather").get(0);

            String temperature = currentCondition.path("temp_C").asText();
            String feelsLike = currentCondition.path("FeelsLikeC").asText();
            String weatherDesc = currentCondition.path("weatherDesc").get(0).path("value").asText();
            String humidity = currentCondition.path("humidity").asText();
            String windSpeed = currentCondition.path("windspeedKmph").asText();
            String windDir = currentCondition.path("winddir16Point").asText();
            String precipitation = currentCondition.path("precipMM").asText();
            String uvIndex = currentCondition.path("uvIndex").asText();
            
            String maxTemp = weather.path("maxtempC").asText();
            String minTemp = weather.path("mintempC").asText();

            return String.format("""
                    Weather for %s:
                    - Condition: %s
                    - Temperature: %s°C (Feels like: %s°C)
                    - Today's Range: %s°C to %s°C
                    - Humidity: %s%%
                    - Wind: %s km/h %s
                    - Precipitation: %s mm
                    - UV Index: %s
                    """,
                    location,
                    weatherDesc,
                    temperature,
                    feelsLike,
                    minTemp,
                    maxTemp,
                    humidity,
                    windSpeed,
                    windDir,
                    precipitation,
                    uvIndex
            );

        } catch (Exception e) {
            return String.format("Error parsing weather data for '%s': %s", location, e.getMessage());
        }
    }
}
