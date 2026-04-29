package com.froneus.dino.domain.exception;

public class DinosaurExtintException extends RuntimeException{
    public DinosaurExtintException(Long id) {
        super("Cannot modify an EXTINCT dinosaur with id: " + id);
    }
}
