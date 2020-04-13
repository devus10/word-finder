package com.pakisoft.wordfinder.infrastructure.word;

import com.pakisoft.wordfinder.domain.word.Dictionary;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

//@Component
@RequiredArgsConstructor
public class TextFileWordDictionary implements Dictionary {

    private final DictionaryReader dictionaryReader;
    private final DictionaryCache dictionaryCache;

    @Override
    public Optional<String> find(String word) {
        if (dictionaryCache.isEmpty()) {
            dictionaryCache.init(dictionaryReader.readWords());
        }

        return dictionaryCache.getCache().stream()
                .filter(dictionaryWord -> dictionaryWord.equals(word))
                .findAny();
    }

    @Override
    public Set<String> findMatching(String word) {
        if (dictionaryCache.isEmpty()) {
            dictionaryCache.init(dictionaryReader.readWords());
        }

        return dictionaryCache.getCache().stream()
                .filter(dictionaryWord -> dictionaryWord.equals(word))
                .collect(Collectors.toSet());
    }
}
