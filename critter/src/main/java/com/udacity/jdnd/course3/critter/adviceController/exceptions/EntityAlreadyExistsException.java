package com.udacity.jdnd.course3.critter.adviceController.exceptions;

/**
 * Exception thrown when attempting to save an entity that already exists.
 * Typically used when an entity with the same unique identifier or
 * business key is already present in the database.
 */
public class EntityAlreadyExistsException extends RuntimeException {
    
    public EntityAlreadyExistsException(String message) {
        super(message);
    }

}
