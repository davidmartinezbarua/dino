package com.froneus.dino.domain.port.out;

import com.froneus.dino.domain.model.Dinosaur;
import com.froneus.dino.domain.model.DinosaurStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DinosaurRepository {
    Dinosaur save(Dinosaur dinosaur);
    Optional<Dinosaur> findById(Long id);
    List<Dinosaur> findAll();
    void deleteById(Long id);
    boolean existsByName(String name);
    List<Dinosaur> findByStatus(DinosaurStatus status);
    List<Dinosaur> findByStatusNot(DinosaurStatus status);
}
