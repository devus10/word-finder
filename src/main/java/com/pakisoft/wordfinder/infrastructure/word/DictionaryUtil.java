package com.pakisoft.wordfinder.infrastructure.word;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class DictionaryUtil {

    public static Map<String, Set<String>> createMap(Set<String> dictionary) {
        Map<String, Set<String>> map = dictionary.stream()
                .map(DictionaryUtil::sortedAlphabetically)
                .collect(Collectors.toMap(DictionaryUtil::sortedAlphabetically,
                        s -> new HashSet<>(),
                        (s1, s2) -> s1)
                );

        dictionary.forEach(word -> map.get(sortedAlphabetically(word)).add(word));

        return map;
    }

    public static String sortedAlphabetically(String word) {
        return word.chars()
                .sorted()
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
