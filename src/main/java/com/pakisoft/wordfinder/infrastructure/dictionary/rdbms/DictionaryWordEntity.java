package com.pakisoft.wordfinder.infrastructure.dictionary.rdbms;

import com.pakisoft.wordfinder.domain.util.StringUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Getter
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@NoArgsConstructor
public abstract class DictionaryWordEntity {

    @Id
    private String word;

    private String sortedWord;

    public DictionaryWordEntity(String word) {
        this.word = word;
        this.sortedWord = StringUtil.lowerCasedAndSortedAlphabetically(word);
    }
}
