package com.pakisoft.wordfinder.infrastructure.dictionary.rdbms

import com.pakisoft.wordfinder.domain.port.secondary.DictionaryRepository
import com.pakisoft.wordfinder.infrastructure.HardwareProperties
import spock.lang.Specification

import java.util.concurrent.Callable

import static com.pakisoft.wordfinder.domain.dictionary.Language.POLISH
import static java.time.Duration.ofMillis
import static org.awaitility.Awaitility.await

class RdbmsDictionaryRepositoryUT extends Specification {

    PersistedDictionaryFinder persistedDictionaryFinder = Mock()
    HardwareProperties hardwareProperties = Mock()
    def repository = new RdbmsDictionaryRepository(
            hardwareProperties,
            persistedDictionaryFinder
    )

    def "should save a dictionary"() {
        given: 'a dictionary'
        def dictionary = polishDictionary()

        and: 'number of threads'
        hardwareProperties.getCpus() >> 1

        and: 'found persisted dictionary'
        def savedWords = []
        def persistedDictionary = persistedDictionary(savedWords)
        persistedDictionaryFinder.findBy(POLISH) >> persistedDictionary

        when: 'saving a dictionary'
        repository.save(dictionary)

        then: 'a dictionary has been saved'
        waitFor({ savedWords.size() == 3 })
        savedWords.containsAll(['auto', 'bułka', 'cel'])
    }

    private PersistedDictionary persistedDictionary(savedWords) {
        Mock(PersistedDictionary) {
            addIfMissing(_ as String) >> { String string -> savedWords << string }
        }
    }

    private DictionaryRepository.Dictionary polishDictionary() {
        new DictionaryRepository.Dictionary(
                POLISH,
                ['auto', 'bułka', 'cel'] as Set
        )
    }

    private void waitFor(Callable condition) {
        await()
                .pollInterval(ofMillis(1))
                .atMost(ofMillis(100))
                .until(condition)
    }
}
