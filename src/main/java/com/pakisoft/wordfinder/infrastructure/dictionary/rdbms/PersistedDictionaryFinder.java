package com.pakisoft.wordfinder.infrastructure.dictionary.rdbms;

import com.pakisoft.wordfinder.domain.dictionary.Language;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;

@Component
@RequiredArgsConstructor
class PersistedDictionaryFinder<E extends DictionaryWordEntity> {

    private final List<PersistedDictionary<E>> persistedDictionaryRepositories;

    PersistedDictionary<E> findBy(Language language) {
        return persistedDictionaryRepositories.stream()
                .filter(repo -> repo.applicable(language))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException(String.format("Dictionary not found for %s language", language)));
    }
}
