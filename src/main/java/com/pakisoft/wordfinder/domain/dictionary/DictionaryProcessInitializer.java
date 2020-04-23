package com.pakisoft.wordfinder.domain.dictionary;

import com.pakisoft.wordfinder.domain.port.secondary.Scheduler;
import lombok.RequiredArgsConstructor;

import javax.annotation.PostConstruct;

@RequiredArgsConstructor
public class DictionaryProcessInitializer {

    private static final String EVERY_SATURDAY_MIDNIGHT = "0 0 0 * * SAT";

    private final Scheduler scheduler;
    private final DictionaryDomainService dictionaryDomainService;

    @PostConstruct
    public void initialize() {
        dictionaryDomainService.saveDictionaries();

        scheduler.schedule(
                dictionaryDomainService::saveDictionaries,
                EVERY_SATURDAY_MIDNIGHT
        );
    }
}
