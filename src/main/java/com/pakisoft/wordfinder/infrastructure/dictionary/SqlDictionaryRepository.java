package com.pakisoft.wordfinder.infrastructure.dictionary;

import com.pakisoft.wordfinder.domain.dictionary.Language;
import com.pakisoft.wordfinder.domain.port.secondary.DictionaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SqlDictionaryRepository implements DictionaryRepository {

    private final List<com.pakisoft.wordfinder.infrastructure.dictionary.Dictionary> dictionaries;

    @Override
    public void save(Dictionary dictionary) {
        var repository = findDictionaryBy(dictionary.getLanguage());
        dictionary.getWords()
                .forEach(word -> {
                    if (!repository.exists(word)) {
                        repository.add(word);
                    }
                });
    }

    private com.pakisoft.wordfinder.infrastructure.dictionary.Dictionary findDictionaryBy(Language language) {
        return dictionaries.stream()
                .filter(dao -> dao.applicable(language))
                .findFirst()
                .orElseThrow();
    }
}
