package com.pakisoft.wordfinder.domain

import com.pakisoft.wordfinder.domain.word.Word
import com.pakisoft.wordfinder.domain.word.WordDomainService

class WordDomainUT extends DomainSpecification {

    def wordService = new WordDomainService(dictionaryRepository())

    def "for given string should find the word in dictionary with all possibles assembled words"() {
        expect:
        found == wordService.find(inputString, 'en')

        and:
        found.existsInDictionary == existsInDictionary

        where:
        inputString | found                                                | existsInDictionary
        'car'       | Word.create('car', ['car', 'acr'] as Set)            | true
        'Rome'      | Word.create('Rome', ['more', 'rome', 'Rome'] as Set) | true
        'rome'      | Word.create('rome', ['more', 'rome', 'Rome'] as Set) | true
        'oobl'      | Word.create('oobl', ['bool'] as Set)                 | false
        'qwe'       | Word.create('qwe', [] as Set)                        | false
    }
}
