package unit.com.pakisoft.wordfinder.domain

import com.pakisoft.wordfinder.domain.configuration.DomainConfiguration
import com.pakisoft.wordfinder.domain.dictionary.Dictionary
import com.pakisoft.wordfinder.domain.dictionary.DictionaryRetriever
import com.pakisoft.wordfinder.domain.language.Language
import com.pakisoft.wordfinder.domain.port.secondary.DictionaryRepository
import com.pakisoft.wordfinder.domain.port.secondary.WordsRetriever
import spock.lang.Specification

import static com.pakisoft.wordfinder.domain.language.Language.ENGLISH
import static com.pakisoft.wordfinder.domain.language.Language.FRENCH
import static com.pakisoft.wordfinder.domain.language.Language.POLISH
import static com.pakisoft.wordfinder.domain.language.Language.RUSSIAN

class DomainSpecification extends Specification {

    def dictionaryRepository() {
        Mock(DictionaryRepository) {
            save(_ as Dictionary) >> {}
            findByLanguage(_ as Language) >> { Language language ->
                switch (language) {
                    case ENGLISH:
                        return Optional.of(Dictionary.withAnagramsFromString(
                                Dictionary.create(ENGLISH, ['acr', 'bool', 'car', 'more', 'rome', 'Rome'] as Set))
                        )
                    case RUSSIAN:
                        return Optional.of(Dictionary.create(RUSSIAN, ['cyka'] as Set))
                    case POLISH:
                        return Optional.empty()
                }
            }
        }
    }

    Set<DictionaryRetriever> dictionaryRetrievers() {
        def set = DomainConfiguration.instance.dictionaryRetrievers([polishWordsRetriever()] as Set)
        set.addAll([
                new EnglishDictionaryRetriever(),
                new FrenchDictionaryRetriever(),
                new RussianDictionaryRetriever()
        ])
        set
    }

    private class EnglishDictionaryRetriever extends DictionaryRetriever {

        EnglishDictionaryRetriever() {
            super(ENGLISH)
            this.initializeWordsRetriever(englishWordsRetriever())
        }
    }

    private class FrenchDictionaryRetriever extends DictionaryRetriever {

        FrenchDictionaryRetriever() {
            super(FRENCH)
            this.initializeWordsRetriever(frenchWordsRetriever())
        }
    }

    private class RussianDictionaryRetriever extends DictionaryRetriever {

        RussianDictionaryRetriever() {
            super(RUSSIAN)
            this.initializeWordsRetriever(russianWordsRetriever())
        }
    }

    private def polishWordsRetriever() {
        mockWordsRetriever(POLISH, ['auto', 'bok'])
    }

    private def englishWordsRetriever() {
        mockWordsRetriever(ENGLISH, ['acr', 'bool', 'car', 'more', 'Rome', 'rome'])
    }

    private def russianWordsRetriever() {
        mockWordsRetriever(RUSSIAN, ['blyat', 'cyka'])
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
