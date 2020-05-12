package com.pakisoft.wordfinder.infrastructure.dictionary.rdbms.polish

import com.pakisoft.wordfinder.domain.dictionary.Language
import com.pakisoft.wordfinder.infrastructure.dictionary.rdbms.JpaDictionaryWordRepository
import spock.lang.Specification

class PolishPersistedDictionaryUT extends Specification {

    private JpaDictionaryWordRepository jpaDictionaryWordRepository = Mock()
    def dictionary = new PolishPersistedDictionary(jpaDictionaryWordRepository)

    def "should check if dictionary is applicable"() {
        expect:
        dictionary.applicable(Language.POLISH)
    }

    def "should create dictionary word"() {
        when:
        def created = dictionary.createDictionaryWord('word')

        then:
        created instanceof PolishDictionaryWordEntity
        created.word == 'word'
        created.sortedWord == 'dorw'
    }
}
