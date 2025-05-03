package com.example.sinistros.service;

import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class OpenAIService {

    private static final Logger logger = LoggerFactory.getLogger(OpenAIService.class);

    private final ChatClient chatClient;

    public OpenAIService(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    public String analisarTexto(String texto) {
        try {
            return chatClient.call(new Prompt(texto))
                    .getResult()
                    .getOutput()
                    .getContent();
        } catch (Exception e) {
            logger.error("Erro ao chamar modelo IA", e);
            return "Erro ao processar com IA: " + e.getMessage();
        }
    }
}
