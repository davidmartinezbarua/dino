package com.froneus.dino.infrastructure.adapter.out.messaging;

import com.froneus.dino.domain.model.Dinosaur;
import com.froneus.dino.domain.port.out.DinosaurEventPublisher;
import com.froneus.dino.infrastructure.adapter.out.messaging.DinosaurStatusEvent;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class RabbitMQPublisher implements DinosaurEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public RabbitMQPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void publishStatusChange(Dinosaur dinosaur) {
        DinosaurStatusEvent event = new DinosaurStatusEvent(
                dinosaur.getId(),
                dinosaur.getStatus(),
                LocalDateTime.now()
        );

        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE,
                RabbitMQConfig.ROUTING_KEY,
                event
        );
    }
}