package com.pakisoft.wordfinder.domain.dictionary;

import com.pakisoft.wordfinder.domain.port.secondary.DictionaryRepository;
import com.pakisoft.wordfinder.domain.port.secondary.DictionaryRepository.Dictionary;
import com.pakisoft.wordfinder.domain.port.secondary.WordsRetriever;
import com.pakisoft.wordfinder.domain.port.secondary.WordsRetriever.FailedWordsRetrievingException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

@RequiredArgsConstructor
@Getter
@Slf4j
public abstract class DictionaryRetriever {

    private WordsRetriever wordsRetriever;
    private DictionaryRepository dictionaryRepository;
    private final Language language;

    public void initializeWordsRetriever(WordsRetriever wordsRetriever, DictionaryRepository dictionaryRepository) {
        this.wordsRetriever = wordsRetriever;
        this.dictionaryRepository = dictionaryRepository;
    }

    void saveDictionary() throws DictionaryException {
        var language = wordsRetriever.getLanguage();
        log.info("Started to save {} dictionary", language);
        dictionaryRepository.save(
                new Dictionary(language, getWords())
        );
        log.info("Finished to save {} dictionary", language);
    }

    private Set<String> getWords() {
        try {
            return wordsRetriever.getWords();
        } catch (FailedWordsRetrievingException e) {
            throw new DictionaryException(e);
        }
    }
}
