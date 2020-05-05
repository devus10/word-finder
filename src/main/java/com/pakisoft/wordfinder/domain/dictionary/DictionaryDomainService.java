package com.pakisoft.wordfinder.domain.dictionary;

import com.google.common.collect.Sets;
import com.pakisoft.wordfinder.domain.port.primary.DictionaryService;
import com.pakisoft.wordfinder.domain.port.secondary.Scheduler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

import static java.util.concurrent.CompletableFuture.runAsync;

@RequiredArgsConstructor
@Slf4j
public class DictionaryDomainService implements DictionaryService {

    private static final String EVERY_SATURDAY_MIDNIGHT = "0 0 0 * * SAT";

    private final Set<DictionaryRetriever> dictionaryRetrievers;
    private final Scheduler scheduler;

    @Override
    public void saveAndScheduleSaving() {
        runAsync(this::saveDictionaries);
        scheduler.schedule(
                this::saveDictionaries,
                EVERY_SATURDAY_MIDNIGHT
        );
    }

    @Override
    public Set<Language> getSupportedLanguages() {
        return Sets.newHashSet(Language.ENGLISH, Language.POLISH);
    }

    private void saveDictionaries() {
        dictionaryRetrievers.forEach(dictionaryRetriever -> {
            try {
                dictionaryRetriever.saveDictionary();
            } catch (DictionaryException e) {
                log.error("Failed to save {} dictionary", dictionaryRetriever.getLanguage());
            }
        });
    }
}
