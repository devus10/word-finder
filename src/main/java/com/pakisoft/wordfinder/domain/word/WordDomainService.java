package com.pakisoft.wordfinder.domain.word;

import com.pakisoft.wordfinder.domain.dictionary.DictionaryLanguage;
import com.pakisoft.wordfinder.domain.port.primary.WordService;
import com.pakisoft.wordfinder.domain.port.secondary.DictionaryRepository;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@RequiredArgsConstructor
public class WordDomainService implements WordService {

    private final DictionaryRepository dictionaryRepository;

    @Override
    public Word find(String string, String language) {
        Set<String> assembledWords = getAssembledWordsFrom(string, language);
        return Word.create(string, assembledWords);
    }

    private Set<String> getAssembledWordsFrom(String string, String language) {
        DictionaryLanguage dictionaryLanguage = findDictionaryLanguage(language);
        return dictionaryRepository.findByLanguage(dictionaryLanguage)
                .orElseThrow(() -> new IllegalStateException(String.format("No dictionary for language '%s' found.", language)))
                .findWordsAssembledFrom(string);
    }

    private DictionaryLanguage findDictionaryLanguage(String language) {
        return DictionaryLanguage.findByCode(language)
                .orElseThrow(() -> new IllegalStateException("Language not found"));
    }
}
