package com.pakisoft.wordfinder.infrastructure.dictionary.english;

import com.pakisoft.wordfinder.domain.dictionary.Language;
import com.pakisoft.wordfinder.domain.port.secondary.WordsRetriever;

public interface EnglishWordsRetriever extends WordsRetriever {

    @Override
    default Language getLanguage() {
        return Language.ENGLISH;
    }
}
