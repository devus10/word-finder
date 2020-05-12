package com.pakisoft.wordfinder.infrastructure.dictionary.rdbms;

import com.pakisoft.wordfinder.domain.dictionary.Language;
import com.pakisoft.wordfinder.domain.port.secondary.DictionaryWordFinder;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.pakisoft.wordfinder.domain.util.StringUtil.lowerCasedAndSortedAlphabetically;

@RequiredArgsConstructor
public abstract class PersistedDictionary {

    protected final JpaDictionaryWordRepository jpaDictionaryWordRepository;

    void add(String word) {
        jpaDictionaryWordRepository.save(createDictionaryWord(word));
    }

    boolean exists(String word) {
        return jpaDictionaryWordRepository.findByWord(word).isPresent();
    }

    protected abstract boolean applicable(Language language);

    protected abstract DictionaryWordEntity createDictionaryWord(String word);

    DictionaryWordFinder.DictionaryWord find(String word) {
        return new DictionaryWordFinder.DictionaryWord(
                word,
                allWordsAssembledFrom(lowerCasedAndSortedAlphabetically(word))
        );
    }

    private Set<String> allWordsAssembledFrom(String sortedWord) {
        return findBySortedWord(sortedWord).stream()
                .map(DictionaryWordEntity::getWord)
                .collect(Collectors.toSet());
    }

    private List<DictionaryWordEntity> findBySortedWord(String sortedWord) {
        return jpaDictionaryWordRepository.findBySortedWord(sortedWord);
    }
}
