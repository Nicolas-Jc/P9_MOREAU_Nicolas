package com.mediscreen.patient.exception;


public class DataAlreadyExistException extends RuntimeException {
    public DataAlreadyExistException(final String message) {
        super(message);
    }
}