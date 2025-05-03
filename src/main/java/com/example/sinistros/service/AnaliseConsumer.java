package com.example.sinistros.service;

import com.example.sinistros.config.RabbitMQConfig;
import com.example.sinistros.model.AnalisePreditiva;
import com.example.sinistros.repository.AnalisePreditivaRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class AnaliseConsumer {

    private final AnalisePreditivaRepository repository;

    public AnaliseConsumer(AnalisePreditivaRepository repository) {
        this.repository = repository;
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void processarAnalise(AnalisePreditiva analise) {
        // Processa a análise recebida
        repository.save(analise);
    }
}
