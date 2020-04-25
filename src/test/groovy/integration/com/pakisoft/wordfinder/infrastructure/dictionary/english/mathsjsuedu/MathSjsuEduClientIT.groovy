package com.pakisoft.wordfinder.infrastructure.dictionary.english.mathsjsuedu

import com.github.tomakehurst.wiremock.junit.WireMockClassRule
import com.pakisoft.wordfinder.annotation.IntegrationTest
import com.pakisoft.wordfinder.common.PropertiesInitializer
import org.junit.ClassRule
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

import static com.github.tomakehurst.wiremock.client.WireMock.get
import static com.github.tomakehurst.wiremock.client.WireMock.okForContentType

@IntegrationTest(classes = MathSjsuEduConfig, initializers = Initializer)
class MathSjsuEduClientIT extends Specification {

    @ClassRule
    static wireMockRule = new WireMockClassRule(0)

    static {
        wireMockRule.start()
        stubbedMathSjsuEdu()
    }

    @Autowired
    MathSjsuEduClient mathSjsuEduClient

    def "should return english words as text"() {
        expect:
        mathSjsuEduClient.getWords() == 'and\nboard\ncar\n'
    }

    static stubbedMathSjsuEdu() {
        wireMockRule.stubFor(get("/")
                .willReturn(okForContentType("text/plain", 'and\nboard\ncar\n'))
        )
    }

    static class Initializer extends PropertiesInitializer {
        static {
            PropertiesInitializer.properties = [
                    "dictionary.english.math-sjsu-edu.url=${wireMockRule.baseUrl()}"
            ]
        }
    }
}
