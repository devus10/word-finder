package com.pakisoft.wordfinder

import com.github.tomakehurst.wiremock.junit.WireMockClassRule
import com.pakisoft.wordfinder.annotation.FunctionalTest
import com.pakisoft.wordfinder.common.PropertiesInitializer
import groovy.sql.Sql
import org.junit.ClassRule
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.util.FileSystemUtils
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.spock.Testcontainers
import spock.lang.Shared
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
@Testcontainers
class WordFinderFT extends Specification {

    @LocalServerPort
    int port

    @ClassRule
    static wireMockRule = new WireMockClassRule(0)

    @Shared
    static PostgreSQLContainer db = new PostgreSQLContainer('postgres:9.6.17-alpine')
            .withDatabaseName("testdb")
            .withUsername("postgres")
            .withPassword("889")

    static Sql sql
    static {
        wireMockRule.start()
        stubbedSjpPage()
        stubbedSjpZipDownload()
        stubbedMathSjsuEdu()
        db.start()
        sql = Sql.newInstance(db.jdbcUrl, db.username, db.password)
//        sql.execute "create sequence hibernate_sequence;"
//        sql.execute """
//         CREATE TABLE polish_dictionary (
//            id int8 NOT NULL PRIMARY KEY,
//            sorted_word VARCHAR(255),
//            word VARCHAR(255)
//         );
//         """
//        sql.execute """
//         CREATE TABLE english_dictionary (
//            id int8 NOT NULL PRIMARY KEY,
//            sorted_word VARCHAR(255),
//            word VARCHAR(255)
//         );
//         """
    }

    def cleanupSpec() {
        db.stop()
    }

    def "should get the word from polish dictionary after dictionary initialization"() {
        expect:
        Thread.sleep(5000)
        def result = sql.rows "SELECT COUNT(*) FROM polish_dictionary"
//        AwaitUtil.waitFor()
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
        Thread.sleep(1000)

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
                    "dictionary.english.math-sjsu-edu.url=${wireMockRule.baseUrl()}/english",
                    "spring.datasource.url=${db.getJdbcUrl()}"
            ]
        }
    }
}


