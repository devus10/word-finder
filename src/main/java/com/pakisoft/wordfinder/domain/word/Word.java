package com.pakisoft.wordfinder.domain.word;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Collections;
import java.util.Set;

@Getter
@EqualsAndHashCode
public class Word {

    private final String textString;
    private final Boolean existsInDictionary;
    private final Set<String> assembledDictionaryWords;

    public static Word create(String string, Set<String> assembledDictionaryWords) {
        Set<String> words = getOrEmptySet(assembledDictionaryWords);
        boolean exists = wordIsInAssembledWords(string, words);

        return new Word(string, exists, words);
    }

    private static Set<String> getOrEmptySet(Set<String> assembledDictionaryWords) {
        return assembledDictionaryWords != null ? assembledDictionaryWords : Collections.emptySet();
    }

    private static boolean wordIsInAssembledWords(String string, Set<String> words) {
        return words.stream()
                .anyMatch(word -> word.equalsIgnoreCase(string));
    }

    private Word(String textString, Boolean existsInDictionary, Set<String> assembledDictionaryWords) {
        this.textString = textString;
        this.existsInDictionary = existsInDictionary;
        this.assembledDictionaryWords = assembledDictionaryWords;
    }
}
