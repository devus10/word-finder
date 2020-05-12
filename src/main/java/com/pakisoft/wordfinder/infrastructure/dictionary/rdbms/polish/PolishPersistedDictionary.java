package com.pakisoft.wordfinder.infrastructure.dictionary.rdbms.polish;

import com.pakisoft.wordfinder.domain.dictionary.Language;
import com.pakisoft.wordfinder.infrastructure.dictionary.rdbms.DictionaryWordEntity;
import com.pakisoft.wordfinder.infrastructure.dictionary.rdbms.JpaDictionaryWordRepository;
import com.pakisoft.wordfinder.infrastructure.dictionary.rdbms.PersistedDictionary;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
class PolishPersistedDictionary extends PersistedDictionary {

    PolishPersistedDictionary(@Qualifier("jpaPolishDictionaryWordRepository") JpaDictionaryWordRepository jpaDictionaryWordRepository) {
        super(jpaDictionaryWordRepository);
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
