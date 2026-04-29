package com.froneus.dino.infrastructure.adapter.out.persistence;

import com.froneus.dino.domain.model.DinosaurStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DinosaurJpaRepository extends JpaRepository<DinosaurEntity, Long> {
    boolean existsByName(String name);
    List<DinosaurEntity> findByStatus(DinosaurStatus status);
    List<DinosaurEntity> findByStatusNot(DinosaurStatus status);
}