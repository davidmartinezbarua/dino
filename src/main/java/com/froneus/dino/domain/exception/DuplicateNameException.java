package com.froneus.dino.domain.exception;

public class DuplicateNameException extends RuntimeException {
    public DuplicateNameException(String name) {
        super("A dinosaur with name '" + name + "' already exists");
    }
}
