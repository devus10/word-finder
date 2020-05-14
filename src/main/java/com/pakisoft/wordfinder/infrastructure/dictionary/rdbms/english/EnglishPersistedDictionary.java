package com.pakisoft.wordfinder.infrastructure.dictionary.rdbms.english;

import com.pakisoft.wordfinder.domain.dictionary.Language;
import com.pakisoft.wordfinder.infrastructure.dictionary.rdbms.DictionaryWordEntity;
import com.pakisoft.wordfinder.infrastructure.dictionary.rdbms.PersistedDictionary;
import org.springframework.stereotype.Component;

@Component
class EnglishPersistedDictionary extends PersistedDictionary {

    EnglishPersistedDictionary(JpaEnglishDictionaryWordRepository jpaEnglishDictionaryWordRepository) {
        super(jpaEnglishDictionaryWordRepository);
    }

    @Override
    protected DictionaryWordEntity createDictionaryWord(String word) {
        return new EnglishDictionaryWordEntity(word);
    }

    @Override
    protected boolean applicable(Language language) {
        return language == Language.ENGLISH;
    }
}
