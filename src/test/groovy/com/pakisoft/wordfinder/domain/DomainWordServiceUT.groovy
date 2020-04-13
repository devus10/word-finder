package com.pakisoft.wordfinder.domain

import com.pakisoft.wordfinder.domain.word.DomainWordService
import com.pakisoft.wordfinder.domain.word.Word

class DomainWordServiceUT extends DomainSpecification {

    def wordService = new DomainWordService(dictionary())

    def "should find the word in the dictionary"() {
        expect:
        found == wordService.find(inputWord)

        where:
        inputWord | found
        'yudzxc'  | Word.nonExisting('yudzxc')
        'car'     | Word.existing('car')
    }
}
