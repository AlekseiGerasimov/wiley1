package ru.alexey.gerasimov.wiley.lib.exception;

/**
 * The exception that will be thrown when the cache is not handled correctly
 */
public class CacheDataHandleException extends RuntimeException {

    public CacheDataHandleException(String message) {
        super(message);
    }
}
