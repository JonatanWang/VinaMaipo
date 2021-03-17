package com.vinamaipo.hrm.domain.exception;

import org.bson.types.ObjectId;

public class NotFoundException extends RuntimeException{

    public NotFoundException() {}

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(Class<?> clazz, long id) {
        super(String.format("Entity %s with ID %d is not found.", clazz.getSimpleName(), id));
    }

    public NotFoundException(Class<?> clazz, String id) {
        super(String.format("Entity %s with ID %d is not found.", clazz.getSimpleName(), id));
    }

    public NotFoundException(Class<?> clazz, ObjectId id) {
        super(String.format("Entity %s with ID %d is not found.", clazz.getSimpleName(), id));
    }
}
