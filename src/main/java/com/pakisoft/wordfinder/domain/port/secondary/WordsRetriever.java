package com.pakisoft.wordfinder.domain.port.secondary;

import com.pakisoft.wordfinder.domain.dictionary.DictionaryLanguage;

import java.util.Set;

public interface WordsRetriever {

    Set<String> getWords() throws FailedWordsRetrievingException;

    DictionaryLanguage getLanguage();
}
