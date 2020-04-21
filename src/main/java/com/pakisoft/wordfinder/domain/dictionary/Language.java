package com.pakisoft.wordfinder.domain.dictionary;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;
import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum Language {

    ENGLISH("en"),
    FRENCH("fr"),
    POLISH("pl"),
    RUSSIAN("ru");

    private final String code;

    public static Optional<Language> findByCode(String languageCode) {
        return Stream.of(values())
                .filter(dictionaryLanguage -> dictionaryLanguage.code.equals(languageCode))
                .findAny();
    }
}
