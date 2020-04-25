package com.pakisoft.wordfinder

import com.github.tomakehurst.wiremock.junit.WireMockClassRule
import com.pakisoft.wordfinder.annotation.FunctionalTest
import com.pakisoft.wordfinder.common.PropertiesInitializer
import org.junit.ClassRule
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.util.FileSystemUtils
import spock.lang.Specification

import java.nio.file.Paths

import static com.github.tomakehurst.wiremock.client.WireMock.get
import static com.github.tomakehurst.wiremock.client.WireMock.ok
import static com.github.tomakehurst.wiremock.client.WireMock.okForContentType
import static io.restassured.RestAssured.when
import static org.hamcrest.Matchers.equalTo
import static org.hamcrest.Matchers.hasItems
import static org.hamcrest.Matchers.hasSize

@FunctionalTest(initializers = Initializer)
class WordFinderFT extends Specification {

    @LocalServerPort
    int port

    @ClassRule
    static wireMockRule = new WireMockClassRule(0)

    static {
        wireMockRule.start()
        stubbedSjpPage()
        stubbedSjpZipDownload()
        stubbedMathSjsuEdu()
    }

    def "should get the word from polish dictionary after dictionary initialization"() {
        expect:
        when().
                get(url('pl', 'pako')).
        then().
                statusCode(200).
                body('textString', equalTo('pako'),
                        'existsInDictionary', equalTo(true),
                        'dictionaryAnagrams', hasSize(3),
                        'dictionaryAnagrams', hasItems('pako', 'kapo', 'okap')
                )

        cleanup:
        removeDictionariesDirectory()
    }

    def "should get the word from english dictionary after dictionary initialization"() {
        expect:
        when().
                get(url('en', 'board')).
        then().
                statusCode(200).
                body('textString', equalTo('board'),
                        'existsInDictionary', equalTo(true),
                        'dictionaryAnagrams', hasSize(1),
                        'dictionaryAnagrams', hasItems('board')
                )
    }

    def url(language, string) {
        "http://localhost:$port/words/$language:$string"
    }

    static stubbedSjpPage() {
        wireMockRule.stubFor(get("/polish")
                .willReturn(okForContentType("text/html",
                        """
                        <html>
                            <body>
                                <a href="sjp.zip">dictionary</a>
                            </body>
                        </html>
                        """)
                )
        )
    }

    static stubbedSjpZipDownload() {
        wireMockRule.stubFor(get("/polish/sjp.zip")
                .willReturn(
                        ok().withBodyFile("sjp.zip")
                )
        )
    }

    static stubbedMathSjsuEdu() {
        wireMockRule.stubFor(get("/english")
                .willReturn(okForContentType("text/plain", 'and\nboard\ncar\n'))
        )
    }

    static void removeDictionariesDirectory() {
        FileSystemUtils.deleteRecursively(Paths.get('test_dictionaries'))
    }

    static class Initializer extends PropertiesInitializer {
        static {
            PropertiesInitializer.properties = [
                    "dictionary.polish.sjp.url=${wireMockRule.baseUrl()}/polish",
                    "dictionary.english.math-sjsu-edu.url=${wireMockRule.baseUrl()}/english"
            ]
        }
    }
}


