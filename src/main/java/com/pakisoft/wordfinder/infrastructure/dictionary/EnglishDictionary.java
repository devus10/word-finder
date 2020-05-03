package com.pakisoft.wordfinder.infrastructure.dictionary;

import com.pakisoft.wordfinder.domain.dictionary.Language;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class EnglishDictionary extends Dictionary {

    public EnglishDictionary(@Qualifier("jpaEnglishDictionaryWordRepository") JpaDictionaryWordRepository jpaDictionaryWordRepository) {
        super(jpaDictionaryWordRepository);
    }

    @Override
    public void add(String word) {
        jpaDictionaryWordRepository.save(new EnglishDictionaryWordEntity(word));
    }

    @Override
    public boolean applicable(Language language) {
        return language == Language.ENGLISH;
    }
}
