package com.pakisoft.wordfinder.domain.port.secondary;

import com.pakisoft.wordfinder.domain.dictionary.Language;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;

public interface DictionaryWordFinder {

    DictionaryWord find(Language language, String word);

    @AllArgsConstructor
    @Getter
    class DictionaryWord {

        private String word;
        private Set<String> anagrams;
    }
}
