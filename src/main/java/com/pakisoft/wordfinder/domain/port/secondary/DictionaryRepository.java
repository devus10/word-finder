package com.pakisoft.wordfinder.domain.port.secondary;

import com.pakisoft.wordfinder.domain.dictionary.Dictionary;
import com.pakisoft.wordfinder.domain.language.Language;

import java.util.Optional;

public interface DictionaryRepository {

    void save(Dictionary dictionary);

    Optional<Dictionary> findByLanguage(Language language);
}
