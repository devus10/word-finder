package com.pakisoft.wordfinder.domain

import com.pakisoft.wordfinder.domain.word.Word
import com.pakisoft.wordfinder.domain.word.WordDomainService

class WordDomainUT extends DomainSpecification {

    def wordService = new WordDomainService(dictionaryRepository())

    def "for given string should find the word in dictionary with all possibles assembled words"() {
        expect:
        found == wordService.find(inputString, 'en')

        where:
        inputString | found
        'car'       | Word.create('car', ['car', 'acr'] as Set)
        'Rome'      | Word.create('Rome', ['more', 'rome', 'Rome'] as Set)
        'rome'      | Word.create('rome', ['more', 'rome', 'Rome'] as Set)
        'oobl'      | Word.create('oobl', ['bool'] as Set)
        'qwe'       | Word.create('qwe', [] as Set)
    }
}
