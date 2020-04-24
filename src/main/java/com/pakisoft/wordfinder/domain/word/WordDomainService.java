package com.pakisoft.wordfinder.domain.word;

import com.pakisoft.wordfinder.domain.language.Language;
import com.pakisoft.wordfinder.domain.port.primary.WordService;
import com.pakisoft.wordfinder.domain.port.secondary.DictionaryRepository;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@RequiredArgsConstructor
public class WordDomainService implements WordService {

    private final DictionaryRepository dictionaryRepository;

    @Override
    public Word find(String string, String language) {
        var dictionaryAnagrams = getDictionaryAnagramsFrom(string, language);
        return Word.create(string, dictionaryAnagrams);
    }

    private Set<String> getDictionaryAnagramsFrom(String string, String languageCode) {
        Language language = findDictionaryLanguage(languageCode);
        return dictionaryRepository.findByLanguage(language)
                .orElseThrow(() -> new IllegalStateException(String.format("No dictionary for '%s' language found.", language)))
                .findWordsAssembledFrom(string);
    }

    private Language findDictionaryLanguage(String language) {
        return Language.findByCode(language)
                .orElseThrow(() -> new IllegalStateException("Language not found"));
    }
}
