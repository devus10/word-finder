package com.pakisoft.wordfinder.domain.dictionary;

import com.pakisoft.wordfinder.domain.port.secondary.Scheduler;
import lombok.RequiredArgsConstructor;

import javax.annotation.PostConstruct;

@RequiredArgsConstructor
public class DictionaryProcessInitializer {

    private final Scheduler scheduler;
    private final DictionaryDomainService dictionaryDomainService;

    @PostConstruct
    public void initialize() {
        scheduler.schedule(dictionaryDomainService::saveDictionaries);
    }
}
