package com.pakisoft.wordfinder.domain.dictionary;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;
import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum DictionaryLanguage {

    ENGLISH("eng"),
    POLISH("pl");

    private final String code;

    public static Optional<DictionaryLanguage> findByCode(String languageCode) {
        return Stream.of(values())
                .filter(dictionaryLanguage -> dictionaryLanguage.code.equals(languageCode))
                .findAny();
    }
}
