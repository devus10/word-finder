package com.pakisoft.wordfinder.infrastructure.dictionary.rdbms.english;

import com.pakisoft.wordfinder.infrastructure.dictionary.rdbms.JpaDictionaryWordRepository;
import org.springframework.stereotype.Repository;

@Repository
interface JpaEnglishDictionaryWordRepository extends JpaDictionaryWordRepository<EnglishDictionaryWordEntity> {
}
