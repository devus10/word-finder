package com.pakisoft.wordfinder.infrastructure.dictionary.rdbms

import com.pakisoft.wordfinder.domain.dictionary.Language
import com.pakisoft.wordfinder.domain.port.secondary.DictionaryWordFinder
import spock.lang.Specification

class RdbmsDictionaryWordFinderUT extends Specification {

    PersistedDictionaryFinder persistedDictionaryFinder = Mock()
    def finder = new RdbmsDictionaryWordFinder(persistedDictionaryFinder)

    def "should find the dictionary word in given language"() {
        given: 'the dictionary language'
        def language = Language.POLISH

        and: 'persisted dictionary found by language'
        def persistedDictionary = Mock(PersistedDictionary) {
            find('auto') >> new DictionaryWordFinder.DictionaryWord(
                    'auto', ['auto', 'otua'] as Set
            )
        }
        persistedDictionaryFinder.findBy(language) >> persistedDictionary

        when: 'finding the dictionary word'
        def word = finder.find(language, 'auto')

        then:
        word.word == 'auto'
        word.anagrams == ['auto', 'otua'] as Set
    }
}
