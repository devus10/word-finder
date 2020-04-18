package com.pakisoft.wordfinder.domain.port.secondary;

import com.pakisoft.wordfinder.domain.DomainException;

public class FailedWordsRetrievingException extends DomainException {

    public FailedWordsRetrievingException(String message, Throwable cause) {
        super(message, cause);
    }
}
