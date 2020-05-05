package com.pakisoft.wordfinder.infrastructure.dictionary.rdbms

import com.pakisoft.wordfinder.domain.dictionary.Language
import spock.lang.Specification

class PersistedDictionaryFinderUT extends Specification {

    private List<PersistedDictionary> dictionaries = [
            Mock(PersistedDictionary) {
                applicable(Language.POLISH) >> true
            }
    ]
    def finder = new PersistedDictionaryFinder(dictionaries)

    def "should find dictionary by language"() {
        expect:
        finder.findBy(Language.POLISH) == dictionaries[0]
    }

    def "should throw exception when dictionary is not found"() {
        when:
        finder.findBy(Language.ENGLISH) == dictionaries[0]

        then:
        def ex = thrown(NoSuchElementException)
        ex.message == 'Dictionary not found for ENGLISH language'
    }

}
