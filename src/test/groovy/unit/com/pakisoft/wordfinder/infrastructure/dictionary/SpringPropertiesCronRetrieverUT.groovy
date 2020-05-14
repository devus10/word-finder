package com.pakisoft.wordfinder.infrastructure.dictionary

import spock.lang.Specification

class SpringPropertiesCronRetrieverUT extends Specification {

    def cronRetriever = new SpringPropertiesCronRetriever()

    def "should return a cron"() {
        given:
        cronRetriever.dictionarySavingCron = '* * * * * *'

        expect:
        cronRetriever.getDictionarySavingCron() == '* * * * * *'
    }
}
