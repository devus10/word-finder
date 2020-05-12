package com.pakisoft.wordfinder.infrastructure.dictionary.rdbms

import groovy.sql.Sql
import org.springframework.util.StopWatch
import spock.lang.Specification

class CollectionUtilUT extends Specification {

    def "should partition a collection"() {
        when:
        def result = CollectionUtil.partition(collection, numberOfPartitions) as ArrayList

        then:
        result == partitioned

        where:
        collection               | numberOfPartitions | partitioned
        [1, 2]                   | 2                  | [[1], [2]]
        []                       | 2                  | []
        [1, 2, 3, 4, 5]          | 2                  | [[1, 2, 5], [3, 4]]
        [1, 2, 3, 4, 5, 6, 7, 8] | 3                  | [[1, 2, 7, 8], [3, 4], [5, 6]]
    }

    def "should throw an exception when validation fails"() {
        when:
        CollectionUtil.partition(collection, numberOfPartitions) as ArrayList

        then:
        def e = thrown(exception)
        e.message == message

        where:
        collection | numberOfPartitions | exception                      | message
        [1, 2]     | 0                  | IllegalArgumentException.class | 'There must be at least 1 partition'
        [1, 2]     | 3                  | IllegalStateException.class    | 'Collection size cannot be smaller than number of partitions'
    }
}
