package ru.alexey.gerasimov.wiley.app.exception;

public class ErrorCodeNotFoundException extends RuntimeException {

    public ErrorCodeNotFoundException(String message) {
        super(message);
    }
}