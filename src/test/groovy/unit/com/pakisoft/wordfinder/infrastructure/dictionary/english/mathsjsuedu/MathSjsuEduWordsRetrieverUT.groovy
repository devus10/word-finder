package com.pakisoft.wordfinder.infrastructure.dictionary.english.mathsjsuedu

import com.pakisoft.wordfinder.domain.dictionary.Language
import com.pakisoft.wordfinder.domain.port.secondary.WordsRetriever
import spock.lang.Specification

class MathSjsuEduWordsRetrieverUT extends Specification {

    MathSjsuEduClient mathSjsuEduClient = Mock()
    def retriever = new MathSjsuEduWordsRetriever(mathSjsuEduClient)

    def "should return english words"() {
        given:
        mathSjsuEduClient.getWords() >> Optional.of('and\nboard\ncar\n')

        expect:
        retriever.getWords() == ['and', 'board', 'car'] as Set
    }

    def "should throw when math sjs edu endpoint returned HTTP 404"() {
        given:
        mathSjsuEduClient.getWords() >> Optional.empty()

        when:
        retriever.getWords()

        then:
        def ex = thrown(WordsRetriever.FailedWordsRetrievingException)
        ex.message == 'Math sjs edu endpoint returned HTTP 404'
    }

    def "should return English language"() {
        expect:
        retriever.getLanguage() == Language.ENGLISH
    }
}
