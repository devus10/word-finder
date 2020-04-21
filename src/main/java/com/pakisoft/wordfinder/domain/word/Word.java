package com.pakisoft.wordfinder.domain.word;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Collections;
import java.util.Set;

@Getter
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Word {

    private final String string;
    private final Boolean existsInDictionary;
    private final Set<String> stringDictionaryAnagrams;

    public static Word create(String string, Set<String> dictionaryAnagrams) {
        Set<String> words = getOrEmptySet(dictionaryAnagrams);
        boolean exists = stringIsInAnagrams(string, words);

        return new Word(string, exists, words);
    }

    private static Set<String> getOrEmptySet(Set<String> dictionaryAnagrams) {
        return dictionaryAnagrams != null ? dictionaryAnagrams : Collections.emptySet();
    }

    private static boolean stringIsInAnagrams(String string, Set<String> dictionaryAnagrams) {
        return dictionaryAnagrams.stream()
                .anyMatch(word -> word.equalsIgnoreCase(string));
    }
}
