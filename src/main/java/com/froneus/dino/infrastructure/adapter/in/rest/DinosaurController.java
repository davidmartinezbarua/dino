package com.froneus.dino.infrastructure.adapter.in.rest;

import com.froneus.dino.domain.model.Dinosaur;
import com.froneus.dino.domain.port.in.DinosaurPort;
import com.froneus.dino.infrastructure.adapter.in.rest.dto.DinosaurRequest;
import com.froneus.dino.infrastructure.adapter.in.rest.dto.DinosaurResponse;
import com.froneus.dino.infrastructure.adapter.in.rest.mapper.DinosaurMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dinosaur")
public class DinosaurController {

    private final DinosaurPort dinosaurPort;
    private final DinosaurMapper mapper;

    public DinosaurController(DinosaurPort dinosaurPort, DinosaurMapper mapper) {
        this.dinosaurPort = dinosaurPort;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<DinosaurResponse> create(@Valid @RequestBody DinosaurRequest request) {
        Dinosaur dinosaur = dinosaurPort.create(mapper.toDomain(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(dinosaur));
    }

    @GetMapping
    public ResponseEntity<List<DinosaurResponse>> findAll() {
        List<DinosaurResponse> response = dinosaurPort.findAll().stream()
                .map(mapper::toResponse)
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DinosaurResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(mapper.toResponse(dinosaurPort.findById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DinosaurResponse> update(@PathVariable Long id,
                                                   @Valid @RequestBody DinosaurRequest request) {
        Dinosaur updated = dinosaurPort.update(id, mapper.toDomain(request));
        return ResponseEntity.ok(mapper.toResponse(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        dinosaurPort.delete(id);
        return ResponseEntity.noContent().build();
    }
}