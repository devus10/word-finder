package com.pakisoft.wordfinder.infrastructure.context;

import com.pakisoft.wordfinder.domain.dictionary.DictionaryDomainService;
import com.pakisoft.wordfinder.domain.dictionary.DictionaryRetriever;
import com.pakisoft.wordfinder.domain.port.primary.WordService;
import com.pakisoft.wordfinder.domain.port.secondary.DictionaryRepository;
import com.pakisoft.wordfinder.domain.port.secondary.WordsRetriever;
import com.pakisoft.wordfinder.domain.word.WordDomainService;
import com.pakisoft.wordfinder.infrastructure.dictionary.InMemoryDictionaryRepository;
import com.pakisoft.wordfinder.infrastructure.dictionary.PolishWordsRetriever;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

@Configuration
public class ContextConfiguration {

    @Bean
    public WordService wordService(DictionaryRepository dictionaryRepository) {
        return new WordDomainService(dictionaryRepository);
    }

    @Bean
    public DictionaryRepository dictionaryDao() {
        return new InMemoryDictionaryRepository();
    }

    @Bean
    public DictionaryDomainService dictionaryService(DictionaryRepository dictionaryRepository, Set<DictionaryRetriever> dictionaryRetrievers) {
        return new DictionaryDomainService(dictionaryRepository, dictionaryRetrievers);
    }
}
