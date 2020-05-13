package com.pakisoft.wordfinder.infrastructure.dictionary.rdbms

import com.pakisoft.wordfinder.domain.dictionary.Language
import spock.lang.Specification
import spock.lang.Unroll

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

    @Unroll
    def "should add a word to a persisted dictionary"() {
        given:
        jpaDictionaryWordRepository.findByWord('word') >> found

        when:
        persistedDictionary.addIfMissing('word')

        then:
        times * jpaDictionaryWordRepository.add(_)

        where:
        found                               | times
        Optional.of(dictionaryWordEntity()) | 0
        Optional.empty()                    | 1
    }

    private def dictionaryWordEntity(String word = 'word') {
        return Mock(DictionaryWordEntity) {
            getWord() >> word
        }
    }

}
