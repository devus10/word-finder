package com.pakisoft.wordfinder.infrastructure.dictionary;

import com.pakisoft.wordfinder.domain.dictionary.Dictionary;
import com.pakisoft.wordfinder.domain.dictionary.DictionaryLanguage;
import com.pakisoft.wordfinder.domain.port.secondary.DictionaryRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class InMemoryDictionaryRepository implements DictionaryRepository {

    private Set<Dictionary> dictionaries = new HashSet<>();

    @Override
    public void save(Dictionary dictionary) {
        dictionaries.add(dictionary);
    }

    @Override
    public Optional<Dictionary> findByLanguage(DictionaryLanguage language) {
        return dictionaries.stream()
                .filter(dictionary -> language.equals(dictionary.getLanguage()))
                .findFirst();
    }
}
