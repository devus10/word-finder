package com.pakisoft.wordfinder.infrastructure.dictionary;

import com.pakisoft.wordfinder.domain.dictionary.Language;
import com.pakisoft.wordfinder.domain.util.StringUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Getter
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@NoArgsConstructor
abstract class DictionaryWordEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String word;

    private String sortedWord;

    DictionaryWordEntity(String word) {
        this.word = word;
        this.sortedWord = StringUtil.lowerCasedAndSortedAlphabetically(word);
    }
}
