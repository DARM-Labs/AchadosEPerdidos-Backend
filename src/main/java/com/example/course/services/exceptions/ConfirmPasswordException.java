package com.example.course.services.exceptions;

public class ConfirmPasswordException extends RuntimeException {
    public ConfirmPasswordException(String message) {
        super(message);
    }
}
