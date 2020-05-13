package com.pakisoft.wordfinder.application.dictionary

import com.pakisoft.wordfinder.domain.port.primary.DictionaryService
import spock.lang.Specification

class DictionaryProcessInitializerUT extends Specification {

    private DictionaryService dictionaryService = Mock()
    def initializer = new DictionaryProcessInitializer(dictionaryService)

    def "should initialize dictionary process"() {
        when:
        initializer.initialize()

        then:
        1 * dictionaryService.saveAndScheduleSaving()
    }
}
