package com.pakisoft.wordfinder.domain.port.primary;

import com.pakisoft.wordfinder.domain.dictionary.Language;

import java.util.Set;

public interface DictionaryService {

    void saveAndScheduleSaving();

    Set<Language> getSupportedLanguages();
}
