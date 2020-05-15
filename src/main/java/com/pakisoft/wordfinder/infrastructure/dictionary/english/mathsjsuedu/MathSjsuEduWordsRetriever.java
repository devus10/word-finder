package com.pakisoft.wordfinder.infrastructure.dictionary.english.mathsjsuedu;

import com.pakisoft.wordfinder.infrastructure.dictionary.english.EnglishWordsRetriever;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Slf4j
public class MathSjsuEduWordsRetriever implements EnglishWordsRetriever {

    private static final String WORDS_DELIMITER = "\n";

    private final MathSjsuEduClient mathSjsuEduClient;

    @Override
    public Set<String> getWords() throws FailedWordsRetrievingException {
        log.info("Started to retrieve English words from Math sjs edu");
        var words = mathSjsuEduClient.getWords();
        if (words.isPresent()) {
            log.info("Finished to retrieve English words from Math sjs edu");
            return Stream.of(words.get().split(WORDS_DELIMITER)).collect(Collectors.toCollection(TreeSet::new));
        }

        throw new FailedWordsRetrievingException("Math sjs edu endpoint returned HTTP 404");
    }
}
