package com.pakisoft.wordfinder.infrastructure.dictionary.rdbms;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface JpaDictionaryWordRepository<E extends DictionaryWordEntity> extends JpaRepository<E, Long> {

    List<DictionaryWordEntity> findBySortedWord(String sortedWord);

    Optional<DictionaryWordEntity> findByWord(String word);
}
