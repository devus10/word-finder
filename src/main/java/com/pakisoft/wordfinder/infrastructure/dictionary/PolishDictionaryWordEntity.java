package com.pakisoft.wordfinder.infrastructure.dictionary;

import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@NoArgsConstructor
@Table(name = "polish_dictionary")
class PolishDictionaryWordEntity extends DictionaryWordEntity {

    PolishDictionaryWordEntity(String word) {
        super(word);
    }
}
