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
@Getter()
@EqualsAndHashCode(of = {"language"})
@Slf4j
public class Dictionary {

    private final DictionaryLanguage language;
    private final Set<String> words;
    private final Map<String, Set<String>> stringsWithAssembledWordsDictionary;

    public static Dictionary create(DictionaryLanguage language, Set<String> words) {
        log.info("started to create dictionary");
        var x = new Dictionary(
                language,
                words,
                Collections.emptyMap()
        );
        log.info("finished to create dictionary");
        return x;
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

    public boolean shouldBeOverwrittenBy(Dictionary dictionary) {
        //TODO: slowa moga byc usuwane ze slownika - to tez wymaga updateu
        log.info("start to check contains");
        boolean x = !this.words.containsAll(dictionary.words);
        log.info("finished to check contains");
        return x;
    }

    private static Map<String, Set<String>> createStringsWithAssembledWordsDictionary(Set<String> dictionary) {
        log.info("Started to create the assembled dictionary");
        Map<String, Set<String>> map = dictionary.stream()
                .collect(Collectors.toMap(StringUtil::lowerCasedAndSortedAlphabetically,
                        s -> new HashSet<>(),
                        (s1, s2) -> s1
                        )
                );

        dictionary.forEach(word -> map.get(lowerCasedAndSortedAlphabetically(word)).add(word));
        log.info("Assembled Dictionary created");

        return map;
    }
}
