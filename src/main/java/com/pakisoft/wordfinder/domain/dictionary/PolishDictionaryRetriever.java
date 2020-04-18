package com.pakisoft.wordfinder.domain.dictionary;

import com.pakisoft.wordfinder.domain.port.secondary.WordsRetriever;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class PolishDictionaryRetriever extends DictionaryRetriever {

    public PolishDictionaryRetriever(Set<WordsRetriever> wordsRetrievers) {
        super(wordsRetrievers, DictionaryLanguage.POLISH);
    }
}
