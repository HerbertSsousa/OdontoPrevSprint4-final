package com.example.sinistros.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String QUEUE_NAME = "analise-queue";
    public static final String EXCHANGE_NAME = "analise-exchange";
    public static final String ROUTING_KEY = "analise.routingkey";

    // Fila durável
    @Bean
    public Queue queue() {
        return QueueBuilder.durable(QUEUE_NAME).build(); // Fila será recriada corretamente se deletada
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    @Bean
    public Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }
}
