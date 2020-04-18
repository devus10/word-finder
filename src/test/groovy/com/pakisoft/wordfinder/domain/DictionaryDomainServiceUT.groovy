package com.pakisoft.wordfinder.domain

import com.pakisoft.wordfinder.domain.dictionary.Dictionary
import com.pakisoft.wordfinder.domain.dictionary.DictionaryDomainService

import static com.pakisoft.wordfinder.domain.dictionary.DictionaryLanguage.ENGLISH

class DictionaryDomainServiceUT extends DomainSpecification {

    def dictionaryRepository = dictionaryRepository()
    def dictionaryService = new DictionaryDomainService(
            dictionaryRepository,
            dictionaryRetrievers()
    )

    Dictionary dictionary

    def "should save dictionaries"() {
        when: 'saving dictionaries'
        dictionaryService.saveDictionaries()

        then: 'retrieved dictionaries are saved'
        1 * dictionaryRepository.save(_ as Dictionary) >> { args -> dictionary = args[0] as Dictionary }

        and: 'saved dictionary has correct language and set of words'
        dictionary.language == ENGLISH
        dictionary.words == ['acr', 'board', 'car', 'more', 'Rome', 'rome'] as Set

        and: 'correct dictionary of strings with assembled words'
        def map = dictionary.stringsWithAssembledWordsDictionary
        map.size() == 3
        map['acr'] == ['acr', 'car'] as Set
        map['emor'] == ['more', 'Rome', 'rome'] as Set
        map['abdor'] == ['board'] as Set
    }
}
