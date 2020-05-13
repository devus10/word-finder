package com.pakisoft.wordfinder.infrastructure.dictionary.rdbms

import org.hibernate.exception.ConstraintViolationException
import org.springframework.dao.DataIntegrityViolationException
import spock.lang.Specification
import spock.lang.Unroll

import java.sql.SQLException

class JpaDictionaryWordRepositoryUT extends Specification {

    @Unroll
    def "should not throw any exception when adding a dictionary word"() {
        when: 'adding a new dictionary word'
        def mock = Mock(DictionaryWordEntity) {
            getWord() >> 'word'
        }
        repository.add(mock)

        then: 'no exception is thrown'
        noExceptionThrown()

        where:
        repository << [
                Spy(JpaDictionaryWordRepository) {
                    save(_) >> { throw primaryKeyViolationException() }
                },
                Spy(JpaDictionaryWordRepository) {
                    save(_) >> null
                }
        ]
    }

    @Unroll
    def "should throw an exception when failed to add a dictionary word"() {
        given: 'a repository'
        def repository = Spy(JpaDictionaryWordRepository) {
            save(_) >> { throw thrownException }
        }

        when: 'adding a new dictionary word'
        def mock = Mock(DictionaryWordEntity) {
            getWord() >> 'word'
        }
        repository.add(mock)

        then: 'exception is thrown'
        thrown(expectedException)

        where:
        thrownException                   | expectedException
        new Exception('exception')        | Exception
        dataIntegrityViolationException() | DataIntegrityViolationException
        withRuntimeExceptionCause()       | DataIntegrityViolationException
        withNullSQLException()            | DataIntegrityViolationException
        withNullSqlState()                | DataIntegrityViolationException
        withUnmatchedSqlState()           | DataIntegrityViolationException
    }

    static DataIntegrityViolationException withUnmatchedSqlState() {
        new DataIntegrityViolationException('msg', new ConstraintViolationException('error', new SQLException('reason', '12345'), 'constraint'))
    }

    static DataIntegrityViolationException withNullSqlState() {
        new DataIntegrityViolationException('msg', new ConstraintViolationException('error', new SQLException('reason', null, 1), 'constraint'))
    }

    static DataIntegrityViolationException withNullSQLException() {
        new DataIntegrityViolationException('msg', new ConstraintViolationException('error', null, 'constraint'))
    }

    static DataIntegrityViolationException withRuntimeExceptionCause() {
        new DataIntegrityViolationException('msg', new RuntimeException('error'))
    }

    static DataIntegrityViolationException dataIntegrityViolationException() {
        new DataIntegrityViolationException('msg')
    }

    static primaryKeyViolationException() {
        new DataIntegrityViolationException('error',
                new ConstraintViolationException('constraint', new SQLException('reason', '23505'), 'pk'))
    }
}
