package com.pakisoft.wordfinder.infrastructure.dictionary;

import org.springframework.stereotype.Repository;

@Repository
public interface JpaPolishDictionaryWordRepository extends JpaDictionaryWordRepository<PolishDictionaryWordEntity> {
}
