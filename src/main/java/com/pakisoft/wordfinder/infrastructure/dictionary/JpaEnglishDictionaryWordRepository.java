package com.pakisoft.wordfinder.infrastructure.dictionary;

import org.springframework.stereotype.Repository;

@Repository
public interface JpaEnglishDictionaryWordRepository extends JpaDictionaryWordRepository<EnglishDictionaryWordEntity> {
}
