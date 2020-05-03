package com.pakisoft.wordfinder.infrastructure.dictionary;

import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@NoArgsConstructor
@Table(name = "english_dictionary")
public class EnglishDictionaryWordEntity extends DictionaryWordEntity {

    EnglishDictionaryWordEntity(String word) {
        super(word);
    }
}
