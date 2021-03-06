package com.pakisoft.wordfinder.domain.dictionary;

import com.google.common.collect.Sets;
import com.pakisoft.wordfinder.domain.port.primary.DictionaryService;
import com.pakisoft.wordfinder.domain.port.secondary.CronRetriever;
import com.pakisoft.wordfinder.domain.port.secondary.Scheduler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

@RequiredArgsConstructor
@Slf4j
public class DictionaryDomainService implements DictionaryService {

    private final Set<DictionaryRetriever> dictionaryRetrievers;
    private final Scheduler scheduler;
    private final CronRetriever cronRetriever;

    @Override
    public void saveAndScheduleSaving() {
        this.saveDictionaries();
        scheduler.schedule(
                this::saveDictionaries,
                cronRetriever.getDictionarySavingCron()
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
                log.error("Failed to save {} dictionary", dictionaryRetriever.getLanguage(), e);
            }
        });
    }
}
