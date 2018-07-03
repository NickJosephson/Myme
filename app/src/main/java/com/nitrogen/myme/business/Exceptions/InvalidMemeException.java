package com.nitrogen.myme.business.Exceptions;

public class InvalidMemeException extends RuntimeException {
    public InvalidMemeException(final String message) {
        super(message);
    }
}
