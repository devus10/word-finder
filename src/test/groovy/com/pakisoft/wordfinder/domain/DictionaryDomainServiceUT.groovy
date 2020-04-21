package com.pakisoft.wordfinder.domain

import com.pakisoft.wordfinder.domain.dictionary.Dictionary
import com.pakisoft.wordfinder.domain.dictionary.DictionaryDomainService
import com.pakisoft.wordfinder.domain.dictionary.Language

import static com.pakisoft.wordfinder.domain.dictionary.Language.*

class DictionaryDomainServiceUT extends DomainSpecification {

    def dictionaryRepository = dictionaryRepository()
    def dictionaryService = new DictionaryDomainService(
            dictionaryRepository,
            dictionaryRetrievers()
    )

    Set<Dictionary> dictionaries = []

    def "should save the dictionaries"() {
        when: 'saving dictionaries'
        dictionaryService.saveDictionaries()

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
    }

    private dictionary(Language language) {
        dictionaries.find { it.language == language }
    }
}
