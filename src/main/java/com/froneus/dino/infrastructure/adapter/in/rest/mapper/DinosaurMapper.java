package com.froneus.dino.infrastructure.adapter.in.rest.mapper;

import com.froneus.dino.domain.model.Dinosaur;
import com.froneus.dino.infrastructure.adapter.in.rest.dto.DinosaurRequest;
import com.froneus.dino.infrastructure.adapter.in.rest.dto.DinosaurResponse;
import org.springframework.stereotype.Component;

@Component
public class DinosaurMapper {

    public Dinosaur toDomain(DinosaurRequest request) {
        Dinosaur dinosaur = new Dinosaur();
        dinosaur.setName(request.getName());
        dinosaur.setSpecies(request.getSpecies());
        dinosaur.setDiscoveryDate(request.getDiscoveryDate());
        dinosaur.setExtinctionDate(request.getExtinctionDate());
        return dinosaur;
    }

    public DinosaurResponse toResponse(Dinosaur dinosaur) {
        return new DinosaurResponse(
                dinosaur.getId(),
                dinosaur.getName(),
                dinosaur.getSpecies(),
                dinosaur.getDiscoveryDate(),
                dinosaur.getExtinctionDate(),
                dinosaur.getStatus()
        );
    }
}