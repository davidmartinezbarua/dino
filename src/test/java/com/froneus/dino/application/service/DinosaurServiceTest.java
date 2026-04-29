package com.froneus.dino.application.service;

import com.froneus.dino.application.DinosaurService;
import com.froneus.dino.domain.exception.DinosaurExtintException;
import com.froneus.dino.domain.exception.DinosaurNotFoundException;
import com.froneus.dino.domain.exception.DuplicateNameException;
import com.froneus.dino.domain.model.Dinosaur;
import com.froneus.dino.domain.model.DinosaurStatus;
import com.froneus.dino.domain.port.out.DinosaurEventPublisher;
import com.froneus.dino.domain.port.out.DinosaurRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DinosaurServiceTest {

    @Mock
    private DinosaurRepository dinosaurRepository;

    @Mock
    private DinosaurEventPublisher eventPublisher;

    @InjectMocks
    private DinosaurService dinosaurService;

    private Dinosaur dinosaur;

    @BeforeEach
    void setUp() {
        dinosaur = new Dinosaur(1L, "Rex", "Theropod",
                LocalDateTime.now().minusDays(1),
                LocalDateTime.now().plusDays(1),
                DinosaurStatus.ALIVE);
    }

    @Test
    void create_shouldSaveDinosaur_whenValidData() {
        when(dinosaurRepository.existsByName("Rex")).thenReturn(false);
        when(dinosaurRepository.save(any())).thenReturn(dinosaur);

        Dinosaur result = dinosaurService.create(dinosaur);

        assertNotNull(result);
        assertEquals(DinosaurStatus.ALIVE, result.getStatus());
        verify(dinosaurRepository).save(any());
    }

    @Test
    void create_shouldThrow_whenNameAlreadyExists() {
        when(dinosaurRepository.existsByName("Rex")).thenReturn(true);

        assertThrows(DuplicateNameException.class, () -> dinosaurService.create(dinosaur));
        verify(dinosaurRepository, never()).save(any());
    }

    @Test
    void findById_shouldReturnDinosaur_whenExists() {
        when(dinosaurRepository.findById(1L)).thenReturn(Optional.of(dinosaur));

        Dinosaur result = dinosaurService.findById(1L);

        assertNotNull(result);
        assertEquals("Rex", result.getName());
    }

    @Test
    void findById_shouldThrow_whenNotFound() {
        when(dinosaurRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(DinosaurNotFoundException.class, () -> dinosaurService.findById(1L));
    }

    @Test
    void update_shouldThrow_whenDinosaurIsExtinct() {
        dinosaur.setStatus(DinosaurStatus.EXTINCT);
        when(dinosaurRepository.findById(1L)).thenReturn(Optional.of(dinosaur));

        assertThrows(DinosaurExtintException.class, () -> dinosaurService.update(1L, dinosaur));
        verify(dinosaurRepository, never()).save(any());
    }

    @Test
    void update_shouldThrow_whenNewNameAlreadyExists() {
        Dinosaur updated = new Dinosaur(1L, "Trex", "Theropod",
                LocalDateTime.now().minusDays(1),
                LocalDateTime.now().plusDays(1),
                DinosaurStatus.ALIVE);

        when(dinosaurRepository.findById(1L)).thenReturn(Optional.of(dinosaur));
        when(dinosaurRepository.existsByName("Trex")).thenReturn(true);

        assertThrows(DuplicateNameException.class, () -> dinosaurService.update(1L, updated));
    }

    @Test
    void delete_shouldThrow_whenNotFound() {
        when(dinosaurRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(DinosaurNotFoundException.class, () -> dinosaurService.delete(1L));
        verify(dinosaurRepository, never()).deleteById(any());
    }

    @Test
    void updateStatuses_shouldMarkAsExtinct_whenExtinctionDateReached() {
        dinosaur.setExtinctionDate(LocalDateTime.now().minusHours(1));
        when(dinosaurRepository.findByStatusNot(DinosaurStatus.EXTINCT))
                .thenReturn(List.of(dinosaur));
        when(dinosaurRepository.findByStatus(DinosaurStatus.ALIVE))
                .thenReturn(List.of());

        dinosaurService.updateStatuses();

        verify(dinosaurRepository).save(argThat(d -> d.getStatus() == DinosaurStatus.EXTINCT));
        verify(eventPublisher).publishStatusChange(any());
    }

    @Test
    void updateStatuses_shouldMarkAsEndangered_whenWithin24Hours() {
        dinosaur.setExtinctionDate(LocalDateTime.now().plusHours(12));
        when(dinosaurRepository.findByStatusNot(DinosaurStatus.EXTINCT))
                .thenReturn(List.of());
        when(dinosaurRepository.findByStatus(DinosaurStatus.ALIVE))
                .thenReturn(List.of(dinosaur));

        dinosaurService.updateStatuses();

        verify(dinosaurRepository).save(argThat(d -> d.getStatus() == DinosaurStatus.ENDANGERED));
        verify(eventPublisher).publishStatusChange(any());
    }
}