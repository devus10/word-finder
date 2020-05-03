package com.pakisoft.wordfinder.infrastructure.dictionary;

import com.pakisoft.wordfinder.domain.dictionary.Language;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class PolishDictionary extends Dictionary {

    public PolishDictionary(@Qualifier("jpaPolishDictionaryWordRepository") JpaDictionaryWordRepository jpaDictionaryWordRepository) {
        super(jpaDictionaryWordRepository);
    }

    @Override
    public void add(String word) {
        jpaDictionaryWordRepository.save(new PolishDictionaryWordEntity(word));
    }

    @Override
    public boolean applicable(Language language) {
        return language == Language.POLISH;
    }
}
