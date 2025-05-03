package com.example.sinistros.messaging;

import com.example.sinistros.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQProducerService {

    private final RabbitTemplate rabbitTemplate;

    public RabbitMQProducerService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void enviarAnalise(String idFoto) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY, idFoto);
        System.out.println("📤 Mensagem enviada com ID da foto: " + idFoto);
    }
}
