package com.froneus.dino.infrastructure.adapter.out.messaging;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE = "dinosaur.exchange";
    public static final String QUEUE = "dinosaur.status.queue";
    public static final String ROUTING_KEY = "dinosaur.status.changed";

    @Bean
    public TopicExchange dinosaurExchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Queue dinosaurQueue() {
        return QueueBuilder.durable(QUEUE).build();
    }

    @Bean
    public Binding dinosaurBinding(Queue dinosaurQueue, TopicExchange dinosaurExchange) {
        return BindingBuilder
                .bind(dinosaurQueue)
                .to(dinosaurExchange)
                .with(ROUTING_KEY);
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return template;
    }
}