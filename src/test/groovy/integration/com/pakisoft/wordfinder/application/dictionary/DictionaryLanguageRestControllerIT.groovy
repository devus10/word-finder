package com.pakisoft.wordfinder.application.dictionary

import com.pakisoft.wordfinder.domain.dictionary.Language
import com.pakisoft.wordfinder.domain.port.primary.DictionaryService
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(DictionaryLanguageRestController)
@ActiveProfiles('test')
class DictionaryLanguageRestControllerIT extends Specification {

    @Autowired
    MockMvc mockMvc

    @SpringBean
    DictionaryService dictionaryService = Mock() {
        getSupportedLanguages() >> [Language.POLISH]
    }

    def "should get supported languages"() {
        expect:
        mockMvc.perform(get('/dictionary-languages'))
                .andExpect(status().isOk())
                .andExpect(content().contentType('application/json'))
                .andExpect(jsonPath('$[0].name').value('Polish'))
                .andExpect(jsonPath('$[0].code').value('pl'))
    }
}
