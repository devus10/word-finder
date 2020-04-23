package com.pakisoft.wordfinder

import com.github.tomakehurst.wiremock.junit.WireMockClassRule
import com.pakisoft.wordfinder.annotation.FunctionalTest
import org.junit.ClassRule
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.test.context.support.TestPropertySourceUtils
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

@FunctionalTest(initializers = PropertiesOverrider)
class WordFinderFT extends Specification {

    @LocalServerPort
    int port

    @ClassRule
    static wireMockRule = new WireMockClassRule(0)

    static {
        wireMockRule.start()
        stubbedSjpPage()
        stubbedSjpZipDownload()
    }

    def "should get the word from polish dictionary after dictionary initialization"() {
        expect:
        when().
                get(url('pako')).
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

    def url(string) {
        "http://localhost:$port/words/pl:$string"
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
                        ok()
                                .withBodyFile("sjp.zip")
                )
        )
    }

    static void removeDictionariesDirectory() {
        FileSystemUtils.deleteRecursively(Paths.get('test_dictionaries'))
    }

    static class PropertiesOverrider implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        void initialize(ConfigurableApplicationContext applicationContext) {
            TestPropertySourceUtils.addInlinedPropertiesToEnvironment(
                    applicationContext, "dictionary.polish.sjp.url=${wireMockRule.baseUrl()}/polish");
        }
    }
}


