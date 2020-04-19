package com.pakisoft.wordfinder.domain.dictionary;

import com.pakisoft.wordfinder.domain.port.secondary.DictionaryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

@RequiredArgsConstructor
@Slf4j
public class DictionaryDomainService {

    private final DictionaryRepository dictionaryRepository;
    private final Set<DictionaryRetriever> dictionaryRetrievers;

    @Scheduled(fixedDelayString = "3000")
    public void saveDictionaries() {
        dictionaryRetrievers.forEach(onRead());
    }

    private Consumer<DictionaryRetriever> onRead() {
        return dictionaryRetriever -> {
            getDictionary(dictionaryRetriever)
                    .ifPresentOrElse(onSuccessfullyRetrievedDictionary(), onFailedToRetrieveDictionary(dictionaryRetriever.getLanguage()));
        };
    }

    private Consumer<Dictionary> onSuccessfullyRetrievedDictionary() {
        return retrievedDictionary -> {
            dictionaryRepository.findByLanguage(retrievedDictionary.getLanguage())
                    .ifPresentOrElse(foundDictionary -> {
                        if (foundDictionary.shouldBeOverwrittenBy(retrievedDictionary)) {
                            dictionaryRepository.save(Dictionary.withStringsWithAssembledWordsDictionary(retrievedDictionary));
                            log.info("{} dictionary updated", foundDictionary.getLanguage());
                        } else {
                            log.info("{} dictionary is up-to-date. No update required.", retrievedDictionary.getLanguage());
                        }
                    }, () -> {
                        dictionaryRepository.save(Dictionary.withStringsWithAssembledWordsDictionary(retrievedDictionary));
                        log.info("{} dictionary saved", retrievedDictionary.getLanguage());
                    });
        };
    }

    private Runnable onFailedToRetrieveDictionary(DictionaryLanguage language) {
        return () -> log.info("Dictionary not persisted - Failed to retrieve {} dictionary", language);
    }

    private Optional<Dictionary> getDictionary(DictionaryRetriever dictionaryRetriever) {
        try {
            return Optional.of(dictionaryRetriever.getDictionary());
        } catch (DictionaryException e) {
            log.info(e.getCause().toString());
            return Optional.empty();
        }
    }
}
