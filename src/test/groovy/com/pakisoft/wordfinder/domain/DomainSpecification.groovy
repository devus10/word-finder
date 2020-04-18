package com.pakisoft.wordfinder.domain

import com.pakisoft.wordfinder.domain.dictionary.Dictionary
import com.pakisoft.wordfinder.domain.dictionary.DictionaryLanguage
import com.pakisoft.wordfinder.domain.port.secondary.WordsRetriever
import com.pakisoft.wordfinder.domain.port.secondary.DictionaryRepository
import spock.lang.Specification

import static com.pakisoft.wordfinder.domain.dictionary.DictionaryLanguage.ENGLISH

class DomainSpecification extends Specification {

    def dictionaryRepository() {
        Mock(DictionaryRepository) {
            save(_ as Dictionary) >> {}
            findByLanguage(_ as DictionaryLanguage) >> { args ->
                final lang = args[0] as DictionaryLanguage
                if (lang == ENGLISH) {
                    return Optional.of(Dictionary.withStringsWithAssembledWordsDictionary(Dictionary.create(
                            ENGLISH, ['acr', 'board', 'car', 'more', 'Rome', 'rome'] as Set)
                    ))
                }
            }
        }
    }

    def dictionaryRetrievers() {
        [
                Mock(WordsRetriever) {
                    getDictionary() >> {
                        Dictionary.create(ENGLISH, ['acr', 'board', 'car', 'more', 'Rome', 'rome'] as Set)
                    }
                }
        ] as Set
    }
}
