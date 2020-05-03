package com.pakisoft.wordfinder.infrastructure.dictionary;

import com.pakisoft.wordfinder.domain.dictionary.Language;
import com.pakisoft.wordfinder.domain.port.secondary.DictionaryWordFinder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SqlDictionaryWordFinder implements DictionaryWordFinder {

    private final List<Dictionary> dictionaryRepositories;

    @Override
    public DictionaryWord find(Language language, String word) {
        return findRepositoryByLanguage(language).find(word);
    }

    private Dictionary findRepositoryByLanguage(Language language) {
        return dictionaryRepositories.stream()
                .filter(repo -> repo.applicable(language))
                .findFirst()
                .orElseThrow();
    }
}
