package com.pakisoft.wordfinder.infrastructure.dictionary


import spock.lang.Specification

class PolishWordsRetrieverUT extends Specification {

    def reader = new PolishWordsRetriever()

    def "test jsoup"() {
        expect:
        reader.downloadFile()
        1
    }

    def "should read words from the dictionary text file"() {
        given:
        reader.dictionaryTextFileName = 'testdictionary.txt'

        when:
        def words = reader.getDictionary()

        then:
        words == ['ABAA', 'Aba', 'abachicie', 'abachit', 'abachitach'] as TreeSet
    }

    def "should throw exception when dictionary text file was not found"() {
        given:
        reader.dictionaryTextFileName = 'non-existing.txt'

        when:
        reader.getDictionary()

        then:
        def ex = thrown(IllegalStateException)
        ex.message == "Cannot find dictionary text file"
    }
}
