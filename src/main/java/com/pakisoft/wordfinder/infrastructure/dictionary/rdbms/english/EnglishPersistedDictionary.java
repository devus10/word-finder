package com.pakisoft.wordfinder.infrastructure.dictionary.rdbms.english;

import com.pakisoft.wordfinder.domain.dictionary.Language;
import com.pakisoft.wordfinder.infrastructure.dictionary.rdbms.DictionaryWordEntity;
import com.pakisoft.wordfinder.infrastructure.dictionary.rdbms.JpaDictionaryWordRepository;
import com.pakisoft.wordfinder.infrastructure.dictionary.rdbms.PersistedDictionary;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
class EnglishPersistedDictionary extends PersistedDictionary {

    EnglishPersistedDictionary(@Qualifier("jpaEnglishDictionaryWordRepository") JpaDictionaryWordRepository jpaDictionaryWordRepository) {
        super(jpaDictionaryWordRepository);
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
