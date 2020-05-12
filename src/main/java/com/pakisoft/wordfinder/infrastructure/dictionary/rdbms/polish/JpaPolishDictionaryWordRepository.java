package com.pakisoft.wordfinder.infrastructure.dictionary.rdbms.polish;

import com.pakisoft.wordfinder.infrastructure.dictionary.rdbms.JpaDictionaryWordRepository;
import org.springframework.stereotype.Repository;

@Repository
interface JpaPolishDictionaryWordRepository extends JpaDictionaryWordRepository<PolishDictionaryWordEntity> {
}
