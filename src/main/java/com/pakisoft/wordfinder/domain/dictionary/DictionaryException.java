package com.pakisoft.wordfinder.domain.dictionary;

import com.pakisoft.wordfinder.domain.DomainException;

public class DictionaryException extends DomainException {

    public DictionaryException(String message, Throwable cause) {
        super(message, cause);
    }

    public DictionaryException(DomainException e) {
        super(e);
    }

    public DictionaryException(String message) {
        super(message);
    }
}
