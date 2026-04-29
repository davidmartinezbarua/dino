package com.froneus.dino.infrastructure.adapter.out.persistence;

import com.froneus.dino.domain.model.Dinosaur;
import com.froneus.dino.domain.model.DinosaurStatus;
import com.froneus.dino.domain.port.out.DinosaurRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class DinosaurRepositoryAdapter implements DinosaurRepository {

    private final DinosaurJpaRepository jpaRepository;

    public DinosaurRepositoryAdapter(DinosaurJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Dinosaur save(Dinosaur dinosaur) {
        DinosaurEntity entity = toEntity(dinosaur);
        DinosaurEntity saved = jpaRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<Dinosaur> findById(Long id) {
        return jpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public List<Dinosaur> findAll() {
        return jpaRepository.findAll().stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public boolean existsByName(String name) {
        return jpaRepository.existsByName(name);
    }

    @Override
    public List<Dinosaur> findByStatus(DinosaurStatus status) {
        return jpaRepository.findByStatus(status).stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public List<Dinosaur> findByStatusNot(DinosaurStatus status) {
        return jpaRepository.findByStatusNot(status).stream()
                .map(this::toDomain)
                .toList();
    }

    private DinosaurEntity toEntity(Dinosaur dinosaur) {
        return new DinosaurEntity(
                dinosaur.getId(),
                dinosaur.getName(),
                dinosaur.getSpecies(),
                dinosaur.getDiscoveryDate(),
                dinosaur.getExtinctionDate(),
                dinosaur.getStatus()
        );
    }

    private Dinosaur toDomain(DinosaurEntity entity) {
        return new Dinosaur(
                entity.getId(),
                entity.getName(),
                entity.getSpecies(),
                entity.getDiscoveryDate(),
                entity.getExtinctionDate(),
                entity.getStatus()
        );
    }
}