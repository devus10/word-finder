package com.pakisoft.wordfinder.infrastructure.word

import spock.lang.Specification

class DictionaryUtilUT extends Specification {

    def "should create a dictionary"() {
        given:
        def dictionary = ['auto', 'bak', 'mizo', 'sopel', 'zimo', 'ziom'] as Set

        when:
        def result = DictionaryUtil.createMap(dictionary)

        then:
        result.size() == 4
        result['aotu'] == ['auto'] as Set
        result['abk'] == ['bak'] as Set
        result['elops'] == ['sopel'] as Set
        result['imoz'] == ['mizo', 'zimo', 'ziom'] as Set
    }
}
