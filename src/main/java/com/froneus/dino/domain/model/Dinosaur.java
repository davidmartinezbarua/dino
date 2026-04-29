package com.froneus.dino.domain.model;

import java.time.LocalDateTime;

public class Dinosaur {

    private Long id;
    private String name;
    private String species;
    private LocalDateTime discoveryDate;
    private LocalDateTime extinctionDate;
    private DinosaurStatus status;

    public Dinosaur() {}

    public Dinosaur(Long id, String name, String species,
                    LocalDateTime discoveryDate, LocalDateTime extinctionDate,
                    DinosaurStatus status) {
        this.id = id;
        this.name = name;
        this.species = species;
        this.discoveryDate = discoveryDate;
        this.extinctionDate = extinctionDate;
        this.status = status;
    }

    public boolean isExtinct() {
        return this.status == DinosaurStatus.EXTINCT;
    }

    public boolean shouldBeEndangered(LocalDateTime now) {
        return this.status == DinosaurStatus.ALIVE
                && extinctionDate != null
                && now.isAfter(extinctionDate.minusHours(24))
                && now.isBefore(extinctionDate);
    }

    public boolean shouldBeExtinct(LocalDateTime now) {
        return extinctionDate != null
                && !now.isBefore(extinctionDate);
    }

    public void validateForCreation() {
        if (discoveryDate != null && extinctionDate != null
                && !discoveryDate.isBefore(extinctionDate)) {
            throw new IllegalArgumentException(
                    "discoveryDate must be before extinctionDate"
            );
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSpecies() { return species; }
    public void setSpecies(String species) { this.species = species; }

    public LocalDateTime getDiscoveryDate() { return discoveryDate; }
    public void setDiscoveryDate(LocalDateTime discoveryDate) {
        this.discoveryDate = discoveryDate;
    }

    public LocalDateTime getExtinctionDate() { return extinctionDate; }
    public void setExtinctionDate(LocalDateTime extinctionDate) {
        this.extinctionDate = extinctionDate;
    }

    public DinosaurStatus getStatus() { return status; }
    public void setStatus(DinosaurStatus status) { this.status = status; }
}