package com.froneus.dino.infrastructure.scheduler;

import com.froneus.dino.application.DinosaurService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DinoScheduler {

    private static final Logger log = LoggerFactory.getLogger(DinoScheduler.class);

    private final DinosaurService dinosaurService;

    public DinoScheduler(DinosaurService dinosaurService) {
        this.dinosaurService = dinosaurService;
    }

    @Scheduled(cron = "0 */10 * * * *")
    public void updateDinosaurStatuses() {
        log.info("Running dinosaur status update scheduler...");
        dinosaurService.updateStatuses();
        log.info("Dinosaur status update completed.");
    }
}