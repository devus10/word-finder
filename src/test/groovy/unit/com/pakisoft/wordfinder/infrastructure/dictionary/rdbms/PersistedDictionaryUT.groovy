package com.pakisoft.wordfinder.infrastructure.dictionary.rdbms

import com.pakisoft.wordfinder.domain.dictionary.Language
import spock.lang.Specification

class PersistedDictionaryUT extends Specification {

    private JpaDictionaryWordRepository jpaDictionaryWordRepository = Mock()
    def persistedDictionary = new PersistedDictionary(jpaDictionaryWordRepository) {
        @Override
        protected boolean applicable(Language language) {
            return false
        }

        @Override
        protected DictionaryWordEntity createDictionaryWord(String word) {
            return dictionaryWordEntity(word)
        }
    }

    def "should check if given word exists in a persisted dictionary"() {
        given:
        jpaDictionaryWordRepository.findByWord(word) >> found

        expect:
        result == persistedDictionary.exists(word)

        where:
        word   | found                               | result
        'word' | Optional.of(dictionaryWordEntity()) | true
        'xyz'  | Optional.empty()                    | false
    }

    private def dictionaryWordEntity(String word = 'word') {
        return Mock(DictionaryWordEntity) {
            getWord() >> word
        }
    }

    def "should find a dictionary word in persisted dictionary"() {
        given:
        def found = [
                dictionaryWordEntity('auto'),
                dictionaryWordEntity('otua')
        ]
        jpaDictionaryWordRepository.findBySortedWord('aotu') >> found

        when:
        def result = persistedDictionary.find('auto')

        then:
        result.word == 'auto'
        result.anagrams == ['auto', 'otua'] as Set
    }

    def "should add a word to a persisted dictionary"() {
        when:
        persistedDictionary.add('word')

        then:
        1 * jpaDictionaryWordRepository.save(_) >> { DictionaryWordEntity entity ->
            assert entity.word == 'word'
        }
    }
}
