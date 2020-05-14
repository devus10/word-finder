package com.pakisoft.wordfinder.infrastructure.dictionary.rdbms.polish;

import com.pakisoft.wordfinder.domain.dictionary.Language;
import com.pakisoft.wordfinder.infrastructure.dictionary.rdbms.DictionaryWordEntity;
import com.pakisoft.wordfinder.infrastructure.dictionary.rdbms.PersistedDictionary;
import org.springframework.stereotype.Component;

@Component
class PolishPersistedDictionary extends PersistedDictionary {

    PolishPersistedDictionary(JpaPolishDictionaryWordRepository jpaPolishDictionaryWordRepository) {
        super(jpaPolishDictionaryWordRepository);
    }

    @Override
    protected DictionaryWordEntity createDictionaryWord(String word) {
        return new PolishDictionaryWordEntity(word);
    }

    @Override
    protected boolean applicable(Language language) {
        return language == Language.POLISH;
    }
}
