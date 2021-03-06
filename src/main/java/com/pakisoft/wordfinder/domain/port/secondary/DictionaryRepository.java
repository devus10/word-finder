package com.pakisoft.wordfinder.domain.port.secondary;

import com.pakisoft.wordfinder.domain.dictionary.Language;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;

public interface DictionaryRepository {

    void save(Dictionary dictionary);

    @AllArgsConstructor
    @Getter
    class Dictionary {
        private Language language;
        private Set<String> words;
    }
}
