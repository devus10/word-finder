package com.pakisoft.wordfinder.domain.dictionary;

import com.pakisoft.wordfinder.domain.language.Language;
import com.pakisoft.wordfinder.domain.port.primary.DictionaryService;
import com.pakisoft.wordfinder.domain.port.secondary.DictionaryRepository;
import com.pakisoft.wordfinder.domain.port.secondary.Scheduler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

@RequiredArgsConstructor
@Slf4j
public class DictionaryDomainService implements DictionaryService {

    private static final String EVERY_SATURDAY_MIDNIGHT = "0 0 0 * * SAT";

    private final DictionaryRepository dictionaryRepository;
    private final Set<DictionaryRetriever> dictionaryRetrievers;
    private final Scheduler scheduler;

    public void saveAndScheduleSaving() {
        saveDictionaries();
        scheduler.schedule(
                this::saveDictionaries,
                EVERY_SATURDAY_MIDNIGHT
        );
    }

    private void saveDictionaries() {
        dictionaryRetrievers.forEach(dictionaryRetriever -> getDictionary(dictionaryRetriever)
                .ifPresentOrElse(
                        onSuccessfullyRetrievedDictionary(),
                        onFailedToRetrieveDictionary(dictionaryRetriever.getLanguage())
                ));
    }

    private Consumer<Dictionary> onSuccessfullyRetrievedDictionary() {
        return retrievedDictionary -> dictionaryRepository.findByLanguage(retrievedDictionary.getLanguage())
                .ifPresentOrElse(
                        onFoundDictionaryByLanguage(retrievedDictionary),
                        onNotFoundDictionaryByLanguage(retrievedDictionary)
                );
    }

    private Consumer<Dictionary> onFoundDictionaryByLanguage(Dictionary retrievedDictionary) {
        return foundDictionary -> {
            if (foundDictionary.shouldBeOverwrittenBy(retrievedDictionary)) {
                updateDictionary(retrievedDictionary);
            } else {
                log.info("{} dictionary is up-to-date. No update required.", retrievedDictionary.getLanguage());
            }
        };
    }

    private void updateDictionary(Dictionary retrievedDictionary) {
        dictionaryRepository.save(Dictionary.withAnagramsFromString(retrievedDictionary));
        log.info("{} dictionary updated", retrievedDictionary.getLanguage());
    }

    private Runnable onNotFoundDictionaryByLanguage(Dictionary retrievedDictionary) {
        return () -> {
            dictionaryRepository.save(Dictionary.withAnagramsFromString(retrievedDictionary));
            log.info("{} dictionary saved", retrievedDictionary.getLanguage());
        };
    }

    private Runnable onFailedToRetrieveDictionary(Language language) {
        return () -> log.info("Dictionary not persisted - Failed to retrieve {} dictionary", language);
    }

    private Optional<Dictionary> getDictionary(DictionaryRetriever dictionaryRetriever) {
        try {
            return Optional.of(dictionaryRetriever.getDictionary());
        } catch (DictionaryException e) {
            log.info(e.details());
            return Optional.empty();
        }
    }
}
