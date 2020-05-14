package com.pakisoft.wordfinder.infrastructure.dictionary.rdbms;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface JpaDictionaryWordRepository<E extends DictionaryWordEntity> extends JpaRepository<E, Long> {

    List<DictionaryWordEntity> findBySortedWord(String sortedWord);

    Optional<DictionaryWordEntity> findByWord(String word);

    default void add(E dictionaryWord) {
        try {
            save(dictionaryWord);
        } catch (DataIntegrityViolationException e) {
            Optional.of(e)
                    .map(Throwable::getCause)
                    .filter(cause -> cause instanceof ConstraintViolationException)
                    .map(cause -> (ConstraintViolationException) cause)
                    .map(ConstraintViolationException::getSQLException)
                    .map(SQLException::getSQLState)
                    .filter(sqlState -> sqlState.equals(Properties.PRIMARY_KEY_CONSTRAINT_VIOLATION_CODE))
                    .ifPresentOrElse(
                            sqlState -> LogHolder.log.warn("Dictionary word '{}' already exists", dictionaryWord.getWord()),
                            () -> {
                                throw e;
                            }
                    );
        }
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    final class LogHolder {
        private static Logger log = LoggerFactory.getLogger(JpaDictionaryWordRepository.class);
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    final class Properties {
        private static String PRIMARY_KEY_CONSTRAINT_VIOLATION_CODE = "23505";
    }
}