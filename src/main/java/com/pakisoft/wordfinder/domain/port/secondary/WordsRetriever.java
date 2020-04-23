package com.pakisoft.wordfinder.domain.port.secondary;

import com.pakisoft.wordfinder.domain.DomainException;
import com.pakisoft.wordfinder.domain.language.Language;

import java.util.Set;

public interface WordsRetriever {

    Set<String> getWords() throws FailedWordsRetrievingException;

    Language getLanguage();

    class FailedWordsRetrievingException extends DomainException {

        public FailedWordsRetrievingException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
