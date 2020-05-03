package com.pakisoft.wordfinder.infrastructure.dictionary.english.mathsjsuedu;

import com.pakisoft.wordfinder.infrastructure.dictionary.english.EnglishWordsRetriever;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Slf4j
public class MathSjsuEduWordsRetriever implements EnglishWordsRetriever {

    private final static String WORDS_DELIMITER = "\n";

    private final MathSjsuEduClient mathSjsuEduClient;

    @Override
    public Set<String> getWords() throws FailedWordsRetrievingException {
        log.info("Started to retrieve English words from Math sjs edu");
        var words = Stream.of(mathSjsuEduClient.getWords().split(WORDS_DELIMITER))
                .collect(Collectors.toSet());
        log.info("Finished to retrieve English words from Math sjs edu");
        return words;
    }
}
