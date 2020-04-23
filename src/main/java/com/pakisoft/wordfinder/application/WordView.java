package com.pakisoft.wordfinder.application;

import com.pakisoft.wordfinder.domain.word.Word;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.Set;

@Value
class WordView {

    String textString;
    Boolean existsInDictionary;
    Set<String> dictionaryAnagrams;

    static WordView from(Word word) {
        return new WordView(word);
    }

    private WordView(Word word) {
        this.textString = word.getString();
        this.existsInDictionary = word.getExistsInDictionary();
        this.dictionaryAnagrams = word.getStringDictionaryAnagrams();
    }
}
