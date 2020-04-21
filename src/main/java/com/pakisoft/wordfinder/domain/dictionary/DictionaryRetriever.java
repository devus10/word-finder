package com.pakisoft.wordfinder.domain.dictionary;

import com.pakisoft.wordfinder.domain.port.secondary.WordsRetriever;
import com.pakisoft.wordfinder.domain.port.secondary.WordsRetriever.FailedWordsRetrievingException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@RequiredArgsConstructor
@Getter
public abstract class DictionaryRetriever {

    private WordsRetriever wordsRetriever;
    private final Language language;

    public void initializeWordsRetriever(WordsRetriever wordsRetriever) {
        this.wordsRetriever = wordsRetriever;
    }

    Dictionary getDictionary() throws DictionaryException {
        return Dictionary.create(language, getWords(wordsRetriever));
    }

    private Set<String> getWords(WordsRetriever wordsRetriever) {
        try {
            return wordsRetriever.getWords();
        } catch (FailedWordsRetrievingException e) {
            throw new DictionaryException(e);
        }
    }
}
