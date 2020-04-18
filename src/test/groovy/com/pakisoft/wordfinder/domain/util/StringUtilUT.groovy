package com.pakisoft.wordfinder.domain.util

import spock.lang.Specification

class StringUtilUT extends Specification {

    def "should make string lower-cased and sorted alphabetically"() {
        expect:
        resultString == StringUtil.lowerCasedAndSortedAlphabetically(inputString)

        where:
        inputString | resultString
        'Rome'      | 'emor'
        'goOd'      | 'dgoo'
    }
}
