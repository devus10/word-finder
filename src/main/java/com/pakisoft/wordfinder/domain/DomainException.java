package com.pakisoft.wordfinder.domain;

public class DomainException extends RuntimeException {

    public DomainException(String message, Throwable cause) {
        super(message, cause);
    }

    public DomainException(DomainException e) {
        super(e);
    }

    public DomainException(String message) {
        super(message);
    }
}
