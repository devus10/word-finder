package com.pakisoft.wordfinder.domain.word;

import com.pakisoft.wordfinder.domain.dictionary.Language;
import com.pakisoft.wordfinder.domain.port.primary.WordService;
import com.pakisoft.wordfinder.domain.port.secondary.DictionaryWordFinder;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@RequiredArgsConstructor
public class WordDomainService implements WordService {

    private final DictionaryWordFinder dictionaryWordFinder;

    @Override
    public Word find(String string, String language) {
        var dictionaryAnagrams = getDictionaryAnagramsFrom(string, language);
        return Word.create(string, dictionaryAnagrams);
    }

    private Set<String> getDictionaryAnagramsFrom(String string, String languageCode) {
        Language language = findDictionaryLanguage(languageCode);
        return dictionaryWordFinder.find(language, string).getAnagrams();
    }

    private Language findDictionaryLanguage(String language) {
        return Language.findByCode(language)
                .orElseThrow(() -> new IllegalStateException("Language not found"));
    }
}
