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
        hardwareProperties.getCpus() >> 3

        and: 'found persisted dictionary'
        def savedWords = []
        def persistedDictionary = persistedDictionary(savedWords)
        persistedDictionaryFinder.findBy(POLISH) >> persistedDictionary

        when: 'saving a dictionary'
        repository.save(dictionary)

        then: 'a dictionary has been saved'
        waitFor({ savedWords.size() == 2 })
        savedWords.containsAll(['auto', 'bułka'])
    }

    private PersistedDictionary persistedDictionary(added) {
        Mock(PersistedDictionary) {
            exists(_ as String) >> { String string ->
                if (string == 'cel') return true
                return false
            }
            add(_ as String) >> { String string -> added << string }
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
