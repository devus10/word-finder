package com.pakisoft.wordfinder.infrastructure.dictionary.english.mathsjsuedu

import spock.lang.Specification

class MathSjsuEduWordsRetrieverUT extends Specification {

    MathSjsuEduClient mathSjsuEduClient = Mock()
    def retriever = new MathSjsuEduWordsRetriever(mathSjsuEduClient)

    def "should return english words"() {
        given:
        mathSjsuEduClient.getWords() >> 'and\nboard\ncar\n'

        expect:
        retriever.getWords() == ['and', 'board', 'car'] as Set
    }
}
