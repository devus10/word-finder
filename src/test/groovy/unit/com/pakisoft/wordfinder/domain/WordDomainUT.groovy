package com.pakisoft.wordfinder.domain

import com.pakisoft.wordfinder.domain.word.Word
import com.pakisoft.wordfinder.domain.word.WordDomainService
import spock.lang.Unroll

class WordDomainUT extends DomainSpecification {

    def wordService = new WordDomainService(dictionaryRepository())

    @Unroll
    def "the '#inputString' string #existsInDictionaryString in dictionary and has #found.stringDictionaryAnagrams anagrams"() {
        expect:
        found == wordService.find(inputString, 'en')

        and:
        found.existsInDictionary == existsInDictionary

        where:
        inputString | found                                                | existsInDictionary | existsInDictionaryString
        'car'       | Word.create('car', ['car', 'acr'] as Set)            | true               | 'exists'
        'Rome'      | Word.create('Rome', ['more', 'rome', 'Rome'] as Set) | true               | 'exists'
        'rome'      | Word.create('rome', ['more', 'rome', 'Rome'] as Set) | true               | 'exists'
        'oobl'      | Word.create('oobl', ['bool'] as Set)                 | false              | 'does not exist'
        'qwe'       | Word.create('qwe', [] as Set)                        | false              | 'does not exist'
    }
}
