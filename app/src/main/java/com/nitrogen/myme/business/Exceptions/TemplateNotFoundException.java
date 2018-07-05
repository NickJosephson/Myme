package com.nitrogen.myme.business.Exceptions;


public class TemplateNotFoundException extends RuntimeException {
    public TemplateNotFoundException(final String message) {
        super(message);
    }
}
