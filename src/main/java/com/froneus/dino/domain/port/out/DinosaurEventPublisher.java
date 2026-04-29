package com.froneus.dino.domain.port.out;

import com.froneus.dino.domain.model.Dinosaur;

public interface DinosaurEventPublisher {
    void publishStatusChange(Dinosaur dinosaur);
}
