package com.pakisoft.wordfinder.domain.dictionary;

import com.pakisoft.wordfinder.domain.port.secondary.FailedWordsRetrievingException;
import com.pakisoft.wordfinder.domain.port.secondary.WordsRetriever;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@RequiredArgsConstructor
@Getter
public abstract class DictionaryRetriever {

    private final Set<WordsRetriever> wordsRetrievers;
    private final DictionaryLanguage language;

    protected Dictionary getDictionary() throws DictionaryException {
        return wordsRetrievers.stream()
                .filter(wordsRetriever -> language.equals(wordsRetriever.getLanguage()))
                .findFirst()
                .map(wordsRetriever -> Dictionary.create(language, getWords(wordsRetriever)))
                .orElseThrow(() -> new DictionaryException("Unable to create dictionary"));
    }

    private Set<String> getWords(WordsRetriever wordsRetriever) {
        try {
            return wordsRetriever.getWords();
        } catch (FailedWordsRetrievingException e) {
            throw new DictionaryException(e);
        }
    }
}
