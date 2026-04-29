package com.froneus.dino.domain.port.in;

import com.froneus.dino.domain.model.Dinosaur;

import java.util.List;

public interface DinosaurPort {

    Dinosaur create(Dinosaur dinosaur);
    List<Dinosaur> findAll();
    Dinosaur findById(Long id);
    Dinosaur update(Long id, Dinosaur dinosaur);
    void delete(Long id);

}
