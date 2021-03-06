package com.pakisoft.wordfinder

import com.github.tomakehurst.wiremock.junit.WireMockClassRule
import com.pakisoft.wordfinder.annotation.FunctionalTest
import com.pakisoft.wordfinder.common.PropertiesInitializer
import groovy.sql.GroovyRowResult
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
import static com.github.tomakehurst.wiremock.client.WireMock.notFound
import static com.github.tomakehurst.wiremock.client.WireMock.ok
import static com.github.tomakehurst.wiremock.client.WireMock.okForContentType
import static com.github.tomakehurst.wiremock.stubbing.Scenario.STARTED
import static com.pakisoft.wordfinder.common.AwaitUtil.waitFor
import static com.pakisoft.wordfinder.common.DbProperties.DB_NAME
import static com.pakisoft.wordfinder.common.DbProperties.PASSWORD
import static com.pakisoft.wordfinder.common.DbProperties.POSTGRES_DOCKER_IMAGE
import static com.pakisoft.wordfinder.common.DbProperties.USERNAME
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
    static PostgreSQLContainer db = new PostgreSQLContainer(POSTGRES_DOCKER_IMAGE)
            .withDatabaseName(DB_NAME)
            .withUsername(USERNAME)
            .withPassword(PASSWORD)

    static Sql sql
    static {
        wireMockRule.start()
        stubbedSjpPage()
        stubbedSjpZipDownload()
        stubbedMathSjsuEdu()
        db.start()
        sql = Sql.newInstance(db.jdbcUrl, db.username, db.password)
    }

    def cleanupSpec() {
        removeDictionariesDirectory()
    }

    def "should get the word from polish dictionary after dictionary initialization"() {
        expect: 'that polish dictionary has been saved'
        waitForTableCount('polish_dictionary', 7)

        def rows = sql.rows "SELECT * FROM polish_dictionary"
        rows.size() == 7
        matches(rows, 'a', 'a')
        matches(rows, 'kapo', 'akop')
        matches(rows, 'okap', 'akop')
        matches(rows, 'polski', 'iklops')
        matches(rows, 'pako', 'akop')
        matches(rows, 'polskich', 'chiklops')
        matches(rows, 'polskie', 'eiklops')

        and: 'the API returns correct data'
        when().
                get(url('pl', 'pako')).
        then().
                statusCode(200).
                body('textString', equalTo('pako'),
                        'existsInDictionary', equalTo(true),
                        'dictionaryAnagrams', hasSize(3),
                        'dictionaryAnagrams', hasItems('pako', 'kapo', 'okap')
                )
    }

    def "should get the word from english dictionary after dictionary initialization"() {
        expect: 'that english dictionary has been saved'
        waitForTableCount('english_dictionary', 4)

        def rows = sql.rows "SELECT * FROM english_dictionary"
        rows.size() == 4
        matches(rows, 'and', 'adn')
        matches(rows, 'car', 'acr')
        matches(rows, 'board', 'abdor')
        matches(rows, 'down', 'dnow')

        and: 'the API returns correct data'
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
        wireMockRule.stubFor(get("/polish/")
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
        wireMockRule.stubFor(get("/english").inScenario('ENGLISH_DICTIONARY')
                .whenScenarioStateIs(STARTED)
                .willSetStateTo('SECOND')
                .willReturn(okForContentType("text/plain", 'and\nboard\ncar\n'))
        )

        wireMockRule.stubFor(get("/english").inScenario('ENGLISH_DICTIONARY')
                .whenScenarioStateIs('SECOND')
                .willSetStateTo('THIRD')
                .willReturn(okForContentType("text/plain", 'and\nboard\ncar\ndown\n'))
        )

        wireMockRule.stubFor(get("/english").inScenario('ENGLISH_DICTIONARY')
                .whenScenarioStateIs('THIRD')
                .willReturn(notFound())
        )
    }

    static void removeDictionariesDirectory() {
        FileSystemUtils.deleteRecursively(Paths.get('test_dictionaries'))
    }

    static void waitForTableCount(String table, count) {
        waitFor({ getCountFrom(table) == count }, 3000)
    }

    static getCountFrom(String table) {
        sql.rows("SELECT COUNT(*) FROM $table".toString()).get(0)['count']
    }

    static matches(List<GroovyRowResult> results, String expectedWord, String expectedSortedWord) {
        return results.any {
            row(it, expectedWord, expectedSortedWord)
        }
    }

    static row(GroovyRowResult result, String expectedWord, String expectedSortedWord) {
        return result['word'] == expectedWord && result['sorted_word'] == expectedSortedWord
    }

    static class Initializer extends PropertiesInitializer {
        static {
            PropertiesInitializer.properties = [
                    "dictionary.polish.sjp.url=${wireMockRule.baseUrl()}/polish/",
                    "dictionary.english.math-sjsu-edu.url=${wireMockRule.baseUrl()}/english/",
                    "spring.datasource.url=${db.getJdbcUrl()}"
            ]
        }
    }
}


