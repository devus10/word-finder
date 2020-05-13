package com.pakisoft.wordfinder.application.dictionary

import com.pakisoft.wordfinder.domain.dictionary.Language
import com.pakisoft.wordfinder.domain.port.primary.DictionaryService
import spock.lang.Specification

import static com.pakisoft.wordfinder.application.dictionary.DictionaryLanguageView.from

class DictionaryLanguageRestControllerUT extends Specification {

    private DictionaryService dictionaryService = Mock()
    def controller = new DictionaryLanguageRestController(dictionaryService)

    def "should return supported dictionary languages"() {
        given:
        dictionaryService.getSupportedLanguages() >> [Language.POLISH]

        expect:
        controller.getSupportedLanguages() == [from(Language.POLISH)] as Set
    }
}
