package com.pakisoft.wordfinder.application.word

import com.pakisoft.wordfinder.domain.port.primary.WordService
import com.pakisoft.wordfinder.domain.word.Word
import spock.lang.Specification

class WordRestControllerUT extends Specification {

    private WordService wordService = Mock()
    def controller = new WordRestController(wordService)

    def "should return a dictionary word"() {
        given:
        wordService.find('word', 'en') >> Word.create('word', ['word', 'rowd'] as Set)

        when:
        def result = controller.getWord('en', 'word')

        then:
        result.textString == 'word'
        result.existsInDictionary
        result.dictionaryAnagrams == ['word', 'rowd'] as Set
    }
}
