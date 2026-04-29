package com.froneus.dino.infrastructure.adapter.out.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class DinosaurEventListener {

    private static final Logger log = LoggerFactory.getLogger(DinosaurEventListener.class);

    @RabbitListener(queues = "${rabbitmq.queue:dinosaur.status.queue}")
    public void handleStatusChange(DinosaurStatusEvent event) {
        log.info("Status change received - DinosaurId: {}, NewStatus: {}, Timestamp: {}",
                event.getDinosaurId(),
                event.getNewStatus(),
                event.getTimestamp());
    }
}