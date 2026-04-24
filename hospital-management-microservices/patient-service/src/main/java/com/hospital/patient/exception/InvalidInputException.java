package com.hospital.patient.exception;

public class InvalidInputException extends RuntimeException {
    
    public InvalidInputException(String message) {
        super(message);
    }
}