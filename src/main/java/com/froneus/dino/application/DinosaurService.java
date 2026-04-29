package com.froneus.dino.application;

import com.froneus.dino.domain.exception.DinosaurExtintException;
import com.froneus.dino.domain.exception.DinosaurNotFoundException;
import com.froneus.dino.domain.exception.DuplicateNameException;
import com.froneus.dino.domain.model.Dinosaur;
import com.froneus.dino.domain.model.DinosaurStatus;
import com.froneus.dino.domain.port.in.DinosaurPort;
import com.froneus.dino.domain.port.out.DinosaurEventPublisher;
import com.froneus.dino.domain.port.out.DinosaurRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DinosaurService implements DinosaurPort {

    private final DinosaurRepository dinosaurRepository;
    private final DinosaurEventPublisher eventPublisher;

    public DinosaurService(DinosaurRepository dinosaurRepository,
                           DinosaurEventPublisher eventPublisher) {
        this.dinosaurRepository = dinosaurRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Dinosaur create(Dinosaur dinosaur) {
        if (dinosaurRepository.existsByName(dinosaur.getName())) {
            throw new DuplicateNameException(dinosaur.getName());
        }

        dinosaur.validateForCreation();
        dinosaur.setStatus(DinosaurStatus.ALIVE);

        return dinosaurRepository.save(dinosaur);
    }

    @Override
    public List<Dinosaur> findAll() {
        return dinosaurRepository.findAll();
    }

    @Override
    public Dinosaur findById(Long id) {
        return dinosaurRepository.findById(id)
                .orElseThrow(() -> new DinosaurNotFoundException(id));
    }

    @Override
    public Dinosaur update(Long id, Dinosaur updated) {
        Dinosaur existing = dinosaurRepository.findById(id)
                .orElseThrow(() -> new DinosaurNotFoundException(id));

        if (existing.isExtinct()) {
            throw new DinosaurExtintException(id);
        }

        if (!existing.getName().equals(updated.getName())
                && dinosaurRepository.existsByName(updated.getName())) {
            throw new DuplicateNameException(updated.getName());
        }

        existing.setName(updated.getName());
        existing.setSpecies(updated.getSpecies());
        existing.setDiscoveryDate(updated.getDiscoveryDate());
        existing.setExtinctionDate(updated.getExtinctionDate());
        existing.validateForCreation();

        return dinosaurRepository.save(existing);
    }

    @Override
    public void delete(Long id) {
        dinosaurRepository.findById(id)
                .orElseThrow(() -> new DinosaurNotFoundException(id));

        dinosaurRepository.deleteById(id);
    }

    public void updateStatuses() {
        LocalDateTime now = LocalDateTime.now();

        List<Dinosaur> toExtinct = dinosaurRepository.findByStatusNot(DinosaurStatus.EXTINCT)
                .stream()
                .filter(d -> d.shouldBeExtinct(now))
                .toList();

        toExtinct.forEach(d -> {
            d.setStatus(DinosaurStatus.EXTINCT);
            dinosaurRepository.save(d);
            eventPublisher.publishStatusChange(d);
        });

        List<Dinosaur> toEndangered = dinosaurRepository.findByStatus(DinosaurStatus.ALIVE)
                .stream()
                .filter(d -> d.shouldBeEndangered(now))
                .toList();

        toEndangered.forEach(d -> {
            d.setStatus(DinosaurStatus.ENDANGERED);
            dinosaurRepository.save(d);
            eventPublisher.publishStatusChange(d);
        });
    }
}