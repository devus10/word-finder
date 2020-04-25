package com.pakisoft.wordfinder.infrastructure.dictionary.english.mathsjsuedu;

import com.pakisoft.wordfinder.infrastructure.dictionary.english.EnglishWordsRetriever;
import lombok.RequiredArgsConstructor;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class MathSjsuEduWordsRetriever implements EnglishWordsRetriever {

    private final static String WORDS_DELIMITER = "\n";

    private final MathSjsuEduClient mathSjsuEduClient;

    @Override
    public Set<String> getWords() throws FailedWordsRetrievingException {
        return Stream.of(mathSjsuEduClient.getWords().split(WORDS_DELIMITER))
                .collect(Collectors.toSet());
    }
}
