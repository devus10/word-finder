package com.pakisoft.wordfinder.infrastructure.dictionary.rdbms.english;

import com.pakisoft.wordfinder.infrastructure.dictionary.rdbms.DictionaryWordEntity;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@NoArgsConstructor
@Table(name = "english_dictionary")
class EnglishDictionaryWordEntity extends DictionaryWordEntity {

    EnglishDictionaryWordEntity(String word) {
        super(word);
    }
}
