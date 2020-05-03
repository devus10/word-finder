package com.pakisoft.wordfinder.infrastructure.dictionary;

import com.pakisoft.wordfinder.domain.dictionary.Language;
import com.pakisoft.wordfinder.domain.port.secondary.DictionaryWordFinder;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.pakisoft.wordfinder.domain.util.StringUtil.lowerCasedAndSortedAlphabetically;

@RequiredArgsConstructor
public abstract class Dictionary {

    protected final JpaDictionaryWordRepository jpaDictionaryWordRepository;

    abstract void add(String word);

    boolean exists(String word) {
        return jpaDictionaryWordRepository.findByWord(word).isPresent();
    }

    abstract boolean applicable(Language language);

    public DictionaryWordFinder.DictionaryWord find(String word) {
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
