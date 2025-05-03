package com.example.sinistros.service;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class OpenAIService {

    private final String apiKey = "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";
    private final String url = "https://openrouter.ai/api/v1/chat/completions";

    public String analisarTexto(String texto) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + apiKey);
        headers.set("HTTP-Referer", "https://openrouter.ai"); // recomendado pela API
        headers.set("X-Title", "ProjetoOdontoAI"); // título opcional do projeto

        Map<String, Object> message = Map.of(
                "role", "user",
                "content", texto
        );

        Map<String, Object> requestBody = Map.of(
                "model", "mistralai/mistral-7b-instruct",
                "messages", List.of(message)
        );

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);
            List<Map<String, Object>> choices = (List<Map<String, Object>>) response.getBody().get("choices");

            if (choices != null && !choices.isEmpty()) {
                Map<String, Object> messageContent = (Map<String, Object>) choices.get(0).get("message");
                return (String) messageContent.get("content");
            }
        } catch (Exception e) {
            return "Erro ao processar requisição OpenRouter: " + e.getMessage();
        }

        return "Nenhuma resposta gerada pela sua IA.";
    }
}
