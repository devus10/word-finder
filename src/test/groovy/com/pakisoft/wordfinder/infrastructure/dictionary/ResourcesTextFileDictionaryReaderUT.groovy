package com.pakisoft.wordfinder.infrastructure.dictionary

import com.pakisoft.wordfinder.infrastructure.word.ResourcesTextFileDictionaryReader
import spock.lang.Specification

class ResourcesTextFileDictionaryReaderUT extends Specification {

    def reader = new ResourcesTextFileDictionaryReader()

    def "should read words from the dictionary text file"() {
        given:
        reader.dictionaryTextFileName = 'testdictionary.txt'

        when:
        def words = reader.readWords()

        then:
        words == ['aba', 'abaa', 'abachicie', 'abachit', 'abachitach'] as TreeSet
    }
}
