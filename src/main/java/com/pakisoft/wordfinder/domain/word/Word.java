package com.pakisoft.wordfinder.domain.word;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Getter
@EqualsAndHashCode
public class Word {

    private final String word;
    private final Boolean existsInDictionary;
    private final Set<String> assembledDictionaryWords;

    public static Word existing(String word) {
        return new Word(
                word,
                true,
                null
        );
    }

    public static Word nonExisting(String word) {
        return new Word(
                word,
                false,
                null);
    }

    public static Word withAssembledDictionaryWords(String string, Set<String> assembledDictionaryWords) {
        return new Word(
                string,
                false,
                assembledDictionaryWords
        );
    }

    private Word(String word, Boolean existsInDictionary, Set<String> assembledDictionaryWords) {
        this.word = word;
        this.existsInDictionary = existsInDictionary;
        this.assembledDictionaryWords = assembledDictionaryWords;
    }
}
