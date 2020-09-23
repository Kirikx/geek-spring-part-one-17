package com.geekbrains.controller;

public class NotFoundException extends RuntimeException {
    private String entityName;

    public NotFoundException(String message, String entityName) {
        super(message);
        this.entityName = entityName;
    }

    public String getEntityName() {
        return entityName;
    }
}
