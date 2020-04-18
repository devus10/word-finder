package com.pakisoft.wordfinder.domain.dictionary;

import com.pakisoft.wordfinder.domain.util.StringUtil;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.pakisoft.wordfinder.domain.util.StringUtil.lowerCasedAndSortedAlphabetically;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@EqualsAndHashCode(of = {"language"})
@Slf4j
public class Dictionary {

    private final DictionaryLanguage language;
    private final Set<String> words;
    private final Map<String, Set<String>> stringsWithAssembledWordsDictionary;

    public static Dictionary create(DictionaryLanguage language, Set<String> dictionary) {
        return new Dictionary(
                language,
                dictionary,
                Collections.emptyMap()
        );
    }

    public static Dictionary withStringsWithAssembledWordsDictionary(Dictionary dictionary) {
        return new Dictionary(
                dictionary.language,
                dictionary.words,
                createStringsWithAssembledWordsDictionary(dictionary.words)
        );
    }

    public Set<String> findWordsAssembledFrom(String string) {
        return stringsWithAssembledWordsDictionary.get(lowerCasedAndSortedAlphabetically(string));
    }

    private static Map<String, Set<String>> createStringsWithAssembledWordsDictionary(Set<String> dictionary) {
        log.info("Started to create the dictionary");
        Map<String, Set<String>> map = dictionary.stream()
                .collect(Collectors.toMap(StringUtil::lowerCasedAndSortedAlphabetically,
                        s -> new HashSet<>(),
                        (s1, s2) -> s1
                        )
                );

        dictionary.forEach(word -> map.get(lowerCasedAndSortedAlphabetically(word)).add(word));
        log.info("Dictionary created");

        return map;
    }
}
