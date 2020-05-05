package com.pakisoft.wordfinder.domain

import com.pakisoft.wordfinder.common.AwaitUtil
import com.pakisoft.wordfinder.domain.dictionary.DictionaryDomainService
import com.pakisoft.wordfinder.domain.dictionary.Language

import static com.pakisoft.wordfinder.domain.dictionary.Language.ENGLISH
import static com.pakisoft.wordfinder.domain.dictionary.Language.POLISH
import static com.pakisoft.wordfinder.domain.dictionary.Language.RUSSIAN

class DictionaryDomainUT extends DomainSpecification {

    def scheduler = scheduler()
    def dictionaryService = new DictionaryDomainService(
            dictionaryRetrievers(),
            scheduler
    )

    def "should save the dictionaries"() {
        when: 'saving and scheduling dictionaries save'
        dictionaryService.saveAndScheduleSaving()

        then:
        AwaitUtil.waitFor({ dictionaries.size() == 2 })

        and: 'polish dictionary has correct language and set of words'
        def polish = dictionary(POLISH)
        polish.language == POLISH
        polish.words == ['auto', 'bok'] as Set

        and: 'russian dictionary has correct language and set of words'
        def russian = dictionary(RUSSIAN)
        russian.language == RUSSIAN
        russian.words == ['blyat', 'cyka'] as Set

        and: 'dictionaries save was scheduled'
        1 * scheduler.schedule(_ as Runnable, '0 0 0 * * SAT')
    }

    def "should return supported dictionary languages"() {
        expect:
        dictionaryService.getSupportedLanguages() == [POLISH, ENGLISH] as Set
    }

    private dictionary(Language language) {
        dictionaries.find { it.language == language }
    }

}
