package com.pakisoft.wordfinder.domain

import com.pakisoft.wordfinder.domain.dictionary.Dictionary
import com.pakisoft.wordfinder.domain.dictionary.DictionaryDomainService
import com.pakisoft.wordfinder.domain.language.Language

import static com.pakisoft.wordfinder.domain.language.Language.ENGLISH
import static com.pakisoft.wordfinder.domain.language.Language.POLISH
import static com.pakisoft.wordfinder.domain.language.Language.RUSSIAN

class DictionaryDomainUT extends DomainSpecification {

    def dictionaryRepository = dictionaryRepository()
    def scheduler = scheduler()
    def dictionaryService = new DictionaryDomainService(
            dictionaryRepository,
            dictionaryRetrievers(),
            scheduler
    )

    Set<Dictionary> dictionaries = []

    def "should save the dictionaries"() {
        when: 'saving and scheduling dictionaries save'
        dictionaryService.saveAndScheduleSaving()

        then: 'retrieved dictionaries are saved'
        2 * dictionaryRepository.save(_ as Dictionary) >> { Dictionary d -> dictionaries.add(d) }

        and: 'polish dictionary has correct language and set of words'
        def polish = dictionary(POLISH)
        polish.language == POLISH
        polish.words == ['auto', 'bok'] as Set

        and: 'correct dictionary of strings with assembled words'
        def polishAnagrams = polish.anagramsFromString
        polishAnagrams.size() == 2
        polishAnagrams['aotu'] == ['auto'] as Set
        polishAnagrams['bko'] == ['bok'] as Set

        and: 'russian dictionary has correct language and set of words'
        def russian = dictionary(RUSSIAN)
        russian.language == RUSSIAN
        russian.words == ['blyat', 'cyka'] as Set

        and: 'correct dictionary of strings with assembled words'
        def russianAnagrams = russian.anagramsFromString
        russianAnagrams.size() == 2
        russianAnagrams['ablty'] == ['blyat'] as Set
        russianAnagrams['acky'] == ['cyka'] as Set

        and: 'english dictionary was already saved'
        def english = dictionaryRepository.findByLanguage(ENGLISH).get()

        and: 'it has correct language and set of words'
        english.language == ENGLISH
        english.words == ['acr', 'bool', 'car', 'more', 'Rome', 'rome'] as Set

        and: 'correct dictionary of strings with assembled words'
        def englishAnagrams = english.anagramsFromString
        englishAnagrams.size() == 3
        englishAnagrams['acr'] == ['acr', 'car'] as Set
        englishAnagrams['emor'] == ['more', 'Rome', 'rome'] as Set
        englishAnagrams['bloo'] == ['bool'] as Set

        and: 'dictionaries save was scheduled'
        1 * scheduler.schedule(_ as Runnable, '0 0 0 * * SAT')
    }

    private dictionary(Language language) {
        dictionaries.find { it.language == language }
    }
}
