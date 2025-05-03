package com.example.sinistros.service;

import com.example.sinistros.config.RabbitMQConfig;
import com.example.sinistros.model.AnalisePreditiva;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class AnaliseProducer {

    private final RabbitTemplate rabbitTemplate;

    public AnaliseProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void enviarAnalise(AnalisePreditiva analise) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY, analise);
    }
}
