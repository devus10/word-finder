package com.pakisoft.wordfinder.domain.dictionary;

import com.pakisoft.wordfinder.domain.DomainException;

class DictionaryException extends DomainException {

    DictionaryException(DomainException e) {
        super(e);
    }
}
