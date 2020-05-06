package com.pakisoft.wordfinder.infrastructure.dictionary.rdbms.english

import com.pakisoft.wordfinder.domain.dictionary.Language
import com.pakisoft.wordfinder.infrastructure.dictionary.rdbms.JpaDictionaryWordRepository
import spock.lang.Specification

class EnglishPersistedDictionaryUT extends Specification {

    private JpaDictionaryWordRepository jpaDictionaryWordRepository = Mock()
    def dictionary = new EnglishPersistedDictionary(jpaDictionaryWordRepository)

    def "should check if dictionary is applicable"() {
        expect:
        dictionary.applicable(Language.ENGLISH)
    }

    def "should create dictionary word"() {
        when:
        def created = dictionary.createDictionaryWord('word')

        then:
        created instanceof EnglishDictionaryWordEntity
        created.word == 'word'
        created.sortedWord == 'dorw'
    }

}
