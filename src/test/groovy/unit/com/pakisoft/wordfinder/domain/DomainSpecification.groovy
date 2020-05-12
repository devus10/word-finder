package com.pakisoft.wordfinder.domain

import com.pakisoft.wordfinder.domain.configuration.DomainConfiguration
import com.pakisoft.wordfinder.domain.dictionary.DictionaryRetriever
import com.pakisoft.wordfinder.domain.dictionary.Language
import com.pakisoft.wordfinder.domain.port.secondary.DictionaryRepository
import com.pakisoft.wordfinder.domain.port.secondary.DictionaryWordFinder
import com.pakisoft.wordfinder.domain.port.secondary.Scheduler
import com.pakisoft.wordfinder.domain.port.secondary.WordsRetriever
import spock.lang.Specification

import static com.pakisoft.wordfinder.domain.dictionary.Language.ENGLISH
import static com.pakisoft.wordfinder.domain.dictionary.Language.FRENCH
import static com.pakisoft.wordfinder.domain.dictionary.Language.POLISH
import static com.pakisoft.wordfinder.domain.dictionary.Language.RUSSIAN

class DomainSpecification extends Specification {

    def dictionaryRepository = createDictionaryRepository()
    def dictionaryWordFinder = createDictionaryWorFinder()

    def dictionaries = []

    private def createDictionaryRepository() {
        Mock(DictionaryRepository) {
            save(_ as DictionaryRepository.Dictionary) >> { DictionaryRepository.Dictionary d -> dictionaries << d }
        }
    }

    private def createDictionaryWorFinder() {
        Mock(DictionaryWordFinder) {
            find(_ as Language, _ as String) >> { args ->
                final word = args[1] as String
                switch (word) {
                    case 'rome':
                        return new DictionaryWordFinder.DictionaryWord(word, ['more', 'Rome'] as Set)
                    case 'qwe':
                        return new DictionaryWordFinder.DictionaryWord(word, [] as Set)
                    case 'tra':
                        return new DictionaryWordFinder.DictionaryWord(word, ['art'] as Set)
                }
            }
        }
    }


    Set<DictionaryRetriever> dictionaryRetrievers() {
        def set = DomainConfiguration.instance.dictionaryRetrievers(
                [polishWordsRetriever(), englishWordsRetriever()] as Set,
                dictionaryRepository
        )
        set.addAll([
                new FrenchDictionaryRetriever(),
        ])
        set
    }

    def scheduler() {
        Mock(Scheduler)
    }

    private class FrenchDictionaryRetriever extends DictionaryRetriever {

        FrenchDictionaryRetriever() {
            super(FRENCH)
            this.initializeWordsRetriever(frenchWordsRetriever(), DomainSpecification.this.dictionaryRepository)
        }
    }

    private def polishWordsRetriever() {
        mockWordsRetriever(POLISH, ['auto', 'bok'])
    }

    private def englishWordsRetriever() {
        mockWordsRetriever(ENGLISH, ['acr', 'bool', 'car'])
    }

    private def frenchWordsRetriever() {
        failingMockWordsRetriever(FRENCH)
    }

    private def mockWordsRetriever(Language language, List<String> words) {
        Mock(WordsRetriever) {
            getLanguage() >> language
            getWords() >> new HashSet<String>(words)
        }
    }

    private def failingMockWordsRetriever(Language language) {
        Mock(WordsRetriever) {
            getLanguage() >> language
            getWords() >> {
                throw new WordsRetriever.FailedWordsRetrievingException(
                        "Failed to retrieve words",
                        new IOException("Failed to read a file")
                )
            }
            //TODO: wyjatek nie loguje sie poprawnie - powinien byc zalaczony cause
        }
    }
}
