package com.froneus.dino.domain.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class DinosaurTest {

    @Test
    void shouldBeEndangered_whenAliveAndWithin24Hours() {
        Dinosaur dinosaur = new Dinosaur(1L, "Rex", "Theropod",
                LocalDateTime.now().minusDays(1),
                LocalDateTime.now().plusHours(12),
                DinosaurStatus.ALIVE);

        assertTrue(dinosaur.shouldBeEndangered(LocalDateTime.now()));
    }

    @Test
    void shouldNotBeEndangered_whenMoreThan24HoursLeft() {
        Dinosaur dinosaur = new Dinosaur(1L, "Rex", "Theropod",
                LocalDateTime.now().minusDays(1),
                LocalDateTime.now().plusHours(25),
                DinosaurStatus.ALIVE);

        assertFalse(dinosaur.shouldBeEndangered(LocalDateTime.now()));
    }

    @Test
    void shouldNotBeEndangered_whenAlreadyEndangered() {
        Dinosaur dinosaur = new Dinosaur(1L, "Rex", "Theropod",
                LocalDateTime.now().minusDays(1),
                LocalDateTime.now().plusHours(12),
                DinosaurStatus.ENDANGERED);

        assertFalse(dinosaur.shouldBeEndangered(LocalDateTime.now()));
    }

    @Test
    void shouldBeExtinct_whenExtinctionDateReached() {
        Dinosaur dinosaur = new Dinosaur(1L, "Rex", "Theropod",
                LocalDateTime.now().minusDays(2),
                LocalDateTime.now().minusHours(1),
                DinosaurStatus.ALIVE);

        assertTrue(dinosaur.shouldBeExtinct(LocalDateTime.now()));
    }

    @Test
    void shouldNotBeExtinct_whenExtinctionDateNotReached() {
        Dinosaur dinosaur = new Dinosaur(1L, "Rex", "Theropod",
                LocalDateTime.now().minusDays(1),
                LocalDateTime.now().plusHours(1),
                DinosaurStatus.ALIVE);

        assertFalse(dinosaur.shouldBeExtinct(LocalDateTime.now()));
    }

    @Test
    void isExtinct_whenStatusIsExtinct() {
        Dinosaur dinosaur = new Dinosaur(1L, "Rex", "Theropod",
                LocalDateTime.now().minusDays(2),
                LocalDateTime.now().minusDays(1),
                DinosaurStatus.EXTINCT);

        assertTrue(dinosaur.isExtinct());
    }

    @Test
    void validateForCreation_shouldThrow_whenDiscoveryDateAfterExtinctionDate() {
        Dinosaur dinosaur = new Dinosaur(1L, "Rex", "Theropod",
                LocalDateTime.now().plusDays(1),
                LocalDateTime.now(),
                DinosaurStatus.ALIVE);

        assertThrows(IllegalArgumentException.class, dinosaur::validateForCreation);
    }

    @Test
    void validateForCreation_shouldNotThrow_whenDatesAreValid() {
        Dinosaur dinosaur = new Dinosaur(1L, "Rex", "Theropod",
                LocalDateTime.now().minusDays(1),
                LocalDateTime.now().plusDays(1),
                DinosaurStatus.ALIVE);

        assertDoesNotThrow(dinosaur::validateForCreation);
    }
}