package com.froneus.dino.infrastructure.adapter.out.persistence;

import com.froneus.dino.domain.model.DinosaurStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "dinosaurs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DinosaurEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String species;

    @Column(nullable = false)
    private LocalDateTime discoveryDate;

    private LocalDateTime extinctionDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DinosaurStatus status;


}