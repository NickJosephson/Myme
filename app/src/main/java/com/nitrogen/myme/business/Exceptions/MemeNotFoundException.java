package com.nitrogen.myme.business.Exceptions;

public class MemeNotFoundException extends RuntimeException {
    public MemeNotFoundException(final String message) {
        super(message);
    }
}
