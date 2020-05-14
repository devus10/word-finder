package com.pakisoft.wordfinder.infrastructure.dictionary.rdbms.english;

import com.pakisoft.wordfinder.domain.dictionary.Language;
import com.pakisoft.wordfinder.infrastructure.dictionary.rdbms.PersistedDictionary;
import org.springframework.stereotype.Component;

@Component
class EnglishPersistedDictionary extends PersistedDictionary<EnglishDictionaryWordEntity> {

    EnglishPersistedDictionary(JpaEnglishDictionaryWordRepository jpaEnglishDictionaryWordRepository) {
        super(jpaEnglishDictionaryWordRepository);
    }

    @Override
    protected EnglishDictionaryWordEntity createDictionaryWord(String word) {
        return new EnglishDictionaryWordEntity(word);
    }

    @Override
    protected boolean applicable(Language language) {
        return language == Language.ENGLISH;
    }
}
