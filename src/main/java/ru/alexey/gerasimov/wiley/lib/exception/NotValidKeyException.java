package ru.alexey.gerasimov.wiley.lib.exception;

/**
 * The exception that will be thrown when the key is not corrected.
 */
public class NotValidKeyException extends RuntimeException {

    public NotValidKeyException(String message) {
        super(message);
    }
}
