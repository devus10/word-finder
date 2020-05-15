package com.pakisoft.wordfinder.application.word


import com.pakisoft.wordfinder.domain.port.primary.WordService
import com.pakisoft.wordfinder.domain.word.Word
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

@WebMvcTest(WordRestController)
@ActiveProfiles('test')
class WordRestControllerIT extends Specification {

    @Autowired
    MockMvc mockMvc

    @SpringBean
    WordService wordService = Mock() {
        find(_ as String, _ as String) >> Word.create('word', ['word', 'rowd'] as Set)
    }

    def "should get a word"() {
        expect:
        mockMvc.perform(get('/words/pl:word'))
                .andExpect(status().isOk())
                .andExpect(content().contentType('application/json'))
                .andExpect(jsonPath('$.textString').value('word'))
                .andExpect(jsonPath('$.existsInDictionary').value(true))
                .andExpect(jsonPath('$.dictionaryAnagrams.[0]').value('word'))
                .andExpect(jsonPath('$.dictionaryAnagrams.[1]').value('rowd'))
    }
}
