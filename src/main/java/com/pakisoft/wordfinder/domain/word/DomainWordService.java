package com.pakisoft.wordfinder.domain.word;

import com.pakisoft.wordfinder.infrastructure.word.DictionaryUtil;
import lombok.RequiredArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class DomainWordService implements WordService {

    private final Dictionary dictionary;

    @Override
    public Word find(String string) {
        return dictionary.find(string)
                .map(Word::existing)
                .orElseGet(() -> Word.nonExisting(string));
    }

    @Override
    public Word assemblyWordFromString(String string) {
        return Word.withAssembledDictionaryWords(string,
                dictionary.findMatching(DictionaryUtil.sortedAlphabetically(string)));
    }

    private static Set<String> permutationsForString(String string) {
        Set<String> stringPermutations = new HashSet<>();
        permutation("", string, stringPermutations);

        return stringPermutations;
    }

    private static void permutation(String prefix, String str, Set<String> permutations) {
        int n = str.length();
        if (n == 0) permutations.add(prefix);
        else {
            for (int i = 0; i < n; i++)
                permutation(prefix + str.charAt(i), str.substring(0, i) + str.substring(i + 1, n), permutations);
        }
    }
}
