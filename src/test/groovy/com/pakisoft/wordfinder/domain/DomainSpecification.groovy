package com.pakisoft.wordfinder.domain

import com.pakisoft.wordfinder.domain.word.Dictionary
import spock.lang.Specification

class DomainSpecification extends Specification {

    def dictionary() {
        def words = ['aeroplane', 'car', 'helicopter']
        new Dictionary() {
            @Override
            Optional<String> find(String word) {
                Optional.ofNullable(words.find { it == word })
            }

            @Override
            Set<String> findMatching(String word) {
                return null
            }
        }
    }
}