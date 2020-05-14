package com.pakisoft.wordfinder.infrastructure.dictionary.polish.sjp

import com.pakisoft.wordfinder.domain.dictionary.Language
import com.pakisoft.wordfinder.domain.port.secondary.WordsRetriever
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import org.slf4j.Logger
import org.springframework.util.FileSystemUtils
import org.springframework.util.ResourceUtils
import spock.lang.Specification

import java.nio.file.Files
import java.nio.file.Paths

import static org.springframework.util.ResourceUtils.CLASSPATH_URL_PREFIX

class SjpPolishWordsRetrieverUT extends Specification {

    private static final URL = 'sjp.pl/'
    private static final SJP_ZIP_FILE_NAME = 'sjp_testdictionary.zip'
    private static final DICTIONARIES_DIRECTORY = 'test_dictionaries'

    private FileReader fileReader = Spy(TextFileReader)
    private FileUtil fileUtil = Mock()
    private HtmlDocumentFetcher jsoupWebScraper = Mock()
    def retriever = new SjpPolishWordsRetriever(
            fileReader,
            jsoupWebScraper,
            fileUtil
    )

    def setup() {
        retriever.log = Mock(Logger)
        retriever.dictionaryUrl = URL
        retriever.targetDirectory = "$DICTIONARIES_DIRECTORY/polish/"
    }

    def "should get words from external dictionary zip file"() {
        given: "dictionary zip file name extracted from SJP web page"
        jsoupWebScraper.getDocument(retriever.dictionaryUrl) >> htmlDocument()

        and: "dictionary zip file downloaded and saved"
        1 * fileUtil.downloadAndSaveFile('sjp.pl/sjp_testdictionary.zip', 'test_dictionaries/polish/sjp_testdictionary.zip') >> {
            saveZipInTargetDirectory()
        }

        when: 'retrieving words'
        def words = retriever.getWords()

        then: 'words are retrieved from the dictionary file'
        words == ['Aba', 'ABAA', 'abachit', 'abachicie', 'abachitach'] as Set

        cleanup:
        removeDictionariesDirectory()
    }

    def "should get words from existing dictionary zip file"() {
        given: 'directory with dictionary file'
        prepareDirectoryWithDictionary()

        and: "dictionary zip file name extracted from SJP web page"
        jsoupWebScraper.getDocument(retriever.dictionaryUrl) >> htmlDocument()

        when: 'retrieving words'
        def words = retriever.getWords()

        then: 'words are retrieved from the dictionary file'
        words == ['a', 'B'] as Set

        and: 'file was not downloaded'
        0 * fileUtil.downloadAndSaveFile(_, _)

        and:
        1 * retriever.log.info("File downloading not required. {} file already exists.", 'test_dictionaries/polish/sjp_testdictionary.zip')

        cleanup:
        removeDictionariesDirectory()
    }

    def "should throw exception when an error occurred"() {
        given: "that failed to get the HTML document of SJP"
        jsoupWebScraper.getDocument(retriever.dictionaryUrl) >> { throw new IOException("error")}

        when: 'retrieving words'
        retriever.getWords()

        then: 'exception is thrown'
        def ex = thrown(WordsRetriever.FailedWordsRetrievingException)
        ex.message == 'Failed to retrieve words due to: java.io.IOException: error'

        cleanup:
        removeDictionariesDirectory()
    }

    def "should return Polish language"() {
        expect:
        retriever.getLanguage() == Language.POLISH
    }

    private void prepareDirectoryWithDictionary() {
        Files.createDirectories(Paths.get(retriever.targetDirectory))
        Files.copy(ResourceUtils.getFile("${CLASSPATH_URL_PREFIX}dictionary/polish/odm.txt").toPath(), Paths.get("$retriever.targetDirectory/odm.txt"))
        Files.copy(ResourceUtils.getFile("${CLASSPATH_URL_PREFIX}dictionary/polish/${SJP_ZIP_FILE_NAME}").toPath(), Paths.get("$retriever.targetDirectory/$SJP_ZIP_FILE_NAME"))
    }

    private void removeDictionariesDirectory() {
        FileSystemUtils.deleteRecursively(Paths.get(DICTIONARIES_DIRECTORY))
    }

    private void saveZipInTargetDirectory() {
        def sourceZipFileFromResources = "${CLASSPATH_URL_PREFIX}dictionary/polish/$SJP_ZIP_FILE_NAME"
        Files.copy(
                ResourceUtils.getFile(sourceZipFileFromResources).toPath(),
                Paths.get("$retriever.targetDirectory/$SJP_ZIP_FILE_NAME")
        )
    }


    private Document htmlDocument() {
        Mock(Document) {
            getElementsByAttributeValueEnding('href', '.zip') >>
                    Elements.of(aElement())
        }
    }

    private aElement() {
        def element = new Element('a')
        element.attributes().add("href", SJP_ZIP_FILE_NAME)
        element
    }

}
