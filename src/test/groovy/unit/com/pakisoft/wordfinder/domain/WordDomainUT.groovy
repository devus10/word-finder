package com.pakisoft.wordfinder.domain

import com.pakisoft.wordfinder.domain.word.Word
import com.pakisoft.wordfinder.domain.word.WordDomainService
import spock.lang.Unroll

class WordDomainUT extends DomainSpecification {

    def wordService = new WordDomainService(dictionaryWordFinder)

    @Unroll
    def "'#inputString' string #existsInDictionaryString in dictionary and has #found.stringDictionaryAnagrams anagrams"() {
        expect:
        found == wordService.find(inputString, 'en')

        and:
        found.existsInDictionary == existsInDictionary

        where:
        inputString | found                                        | existsInDictionary | existsInDictionaryString
        'rome'      | Word.create('rome', ['more', 'Rome'] as Set) | true               | 'exists'
        'qwe'       | Word.create('qwe', [] as Set)                | false              | 'does not exist'
        'tra'       | Word.create('tra', ['art'] as Set)           | false              | 'does not exist'
    }
}
