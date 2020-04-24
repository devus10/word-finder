package com.pakisoft.wordfinder.application.dictionary;

import com.pakisoft.wordfinder.domain.port.primary.DictionaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class DictionaryProcessInitializer {

    private final DictionaryService dictionaryService;

    @PostConstruct
    public void initialize() {
        dictionaryService.saveAndScheduleSaving();
    }
}
