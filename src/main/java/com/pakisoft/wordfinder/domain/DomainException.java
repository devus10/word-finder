package com.pakisoft.wordfinder.domain;

public class DomainException extends RuntimeException {

    public DomainException(String message, Throwable cause) {
        super(message, cause);
    }

    public DomainException(DomainException e) {
        super(e);
    }

    public String details() {
        var cause = this.getCause();
        if (cause != null) {
            return getCause().toString();
        }

        return this.getMessage();
    }
}
