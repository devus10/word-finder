package com.pakisoft.wordfinder.infrastructure.dictionary.polish;

import com.pakisoft.wordfinder.domain.dictionary.Language;
import com.pakisoft.wordfinder.domain.port.secondary.WordsRetriever;

public interface PolishWordsRetriever extends WordsRetriever {

    @Override
    default Language getLanguage() {
        return Language.POLISH;
    }
}
