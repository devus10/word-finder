package com.pakisoft.wordfinder.infrastructure.dictionary.rdbms;

import com.pakisoft.wordfinder.domain.dictionary.Language;
import com.pakisoft.wordfinder.domain.port.secondary.DictionaryWordFinder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class RdbmsDictionaryWordFinder implements DictionaryWordFinder {

    private final PersistedDictionaryFinder persistedDictionaryFinder;

    @Override
    public DictionaryWord find(Language language, String word) {
        return persistedDictionaryFinder.findBy(language)
                .find(word);
    }
}
