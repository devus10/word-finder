package com.pakisoft.wordfinder.domain.dictionary;

import com.pakisoft.wordfinder.domain.language.Language;
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

    private final Language language;
    private final Set<String> words;
    private final Map<String, Set<String>> anagramsFromString;

    static Dictionary create(Language language, Set<String> words) {
        return new Dictionary(
                language,
                words,
                Collections.emptyMap()
        );
    }

    static Dictionary withAnagramsFromString(Dictionary dictionary) {
        return new Dictionary(
                dictionary.language,
                dictionary.words,
                createAnagramsMap(dictionary)
        );
    }

    public Set<String> findWordsAssembledFrom(String string) {
        return anagramsFromString.get(lowerCasedAndSortedAlphabetically(string));
    }

    boolean shouldBeOverwrittenBy(Dictionary dictionary) {
        //TODO: slowa moga byc usuwane ze slownika - to tez wymaga updateu
        return !this.words.containsAll(dictionary.words);
    }

    private static Map<String, Set<String>> createAnagramsMap(Dictionary dictionary) {
        log.info("Starting to create anagrams map for {}", dictionary);
        Map<String, Set<String>> map = dictionary.words.stream()
                .collect(Collectors.toMap(StringUtil::lowerCasedAndSortedAlphabetically,
                        s -> new HashSet<>(),
                        (s1, s2) -> s1
                        )
                );

        dictionary.words.forEach(word -> map.get(lowerCasedAndSortedAlphabetically(word)).add(word));
        log.info("Finished to create anagrams map for {}", dictionary);

        return map;
    }

    @Override
    public String toString() {
        return this.language + " dictionary";
    }
}
