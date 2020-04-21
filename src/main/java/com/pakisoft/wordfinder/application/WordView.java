package com.pakisoft.wordfinder.application;

import com.pakisoft.wordfinder.domain.word.Word;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@RequiredArgsConstructor
@Getter
class WordView {

    private final String textString;
    private final Boolean existsInDictionary;
    private final Set<String> dictionaryAnagrams;

    static WordView from(Word word) {
        return new WordView(word);
    }

    private WordView(Word word) {
        this.textString = word.getString();
        this.existsInDictionary = word.getExistsInDictionary();
        this.dictionaryAnagrams = word.getStringDictionaryAnagrams();
    }
}
