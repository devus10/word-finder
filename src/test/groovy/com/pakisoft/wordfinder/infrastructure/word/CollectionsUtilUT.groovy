package com.pakisoft.wordfinder.infrastructure.word

import spock.lang.Specification

class CollectionsUtilUT extends Specification {

    def "should divide a collection into subsets"() {
        given:
        def inputList = ['a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n']

        when:
        def subsets = CollectionsUtil.divideIntoSubsets(inputList, 5)

        then:
        subsets.size() == 5
        subsets[0] == ['a', 'b', 'k'] as Set
        subsets[1] == ['c', 'd', 'l'] as Set
        subsets[2] == ['e', 'f', 'm'] as Set
        subsets[3] == ['g', 'h', 'n'] as Set
        subsets[4] == ['i', 'j'] as Set
    }

    def "should create a dictionary map"() {
        given:
        def inputList = ['ab', 'ac', 'bd', 'ad'] as Set

        when:
        def map = CollectionsUtil.createDictionaryMap(inputList)

        then:
        map['a' as char] == new HashSet<String>(['ab', 'ac', 'ad'])
        map['b' as char] == new HashSet<String>(['bd'])
    }
}
