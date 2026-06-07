package com.udacity.jdnd.course3.critter.adviceController.exceptions;

/**
 * Exception thrown when attempting to retrieve or update an entity that doesn't exist.
 * Typically used when an entity with the given ID cannot be found in the database.
 */
public class EntityNotFoundException extends RuntimeException {
    
    public EntityNotFoundException(String message) {
        super(message);
    }
}
