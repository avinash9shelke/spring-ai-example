package com.ai.springaitool.tools;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

/**
 * @author avinash
 */
@Service
@Slf4j
public class WikipediaTool {

    @Tool(name = "wikipedia", description = "Get article from wikipedia")
    public String getArticle(String topic) {
        String url = "https://en.wikipedia.org/api/rest_v1/page/summary/" + topic.replace(" ", "_");
        log.info("Fetching article for '{}'", topic);
        try (var client = HttpClient.newHttpClient()) {
            var request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();
            var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception e) {
            return "Failed to fetch article: " + e.getMessage();
        }

    }
}
