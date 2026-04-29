package com.froneus.dino.infrastructure.adapter.in.rest.dto;

import com.froneus.dino.domain.model.DinosaurStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DinosaurResponse {

    private Long id;
    private String name;
    private String species;
    private LocalDateTime discoveryDate;
    private LocalDateTime extinctionDate;
    private DinosaurStatus status;
}