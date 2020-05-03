package com.pakisoft.wordfinder.infrastructure.context;

import com.pakisoft.wordfinder.domain.configuration.DomainConfiguration;
import com.pakisoft.wordfinder.domain.dictionary.DictionaryDomainService;
import com.pakisoft.wordfinder.domain.dictionary.DictionaryRetriever;
import com.pakisoft.wordfinder.domain.port.primary.DictionaryService;
import com.pakisoft.wordfinder.domain.port.primary.WordService;
import com.pakisoft.wordfinder.domain.port.secondary.DictionaryRepository;
import com.pakisoft.wordfinder.domain.port.secondary.DictionaryWordFinder;
import com.pakisoft.wordfinder.domain.port.secondary.Scheduler;
import com.pakisoft.wordfinder.domain.port.secondary.WordsRetriever;
import com.pakisoft.wordfinder.domain.word.WordDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Set;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class ContextConfiguration {

    private final DomainConfiguration domainConfiguration = DomainConfiguration.getInstance();
    private final Set<WordsRetriever> wordsRetrievers;

//    @Bean
//    public DictionaryRead dictionaryRead() {
//        return new DictionaryReadImpl();
//    }

    @Bean
    public WordService wordService(DictionaryWordFinder dictionaryRead) {
        return new WordDomainService(dictionaryRead);
    }

    @Bean
    public DictionaryService dictionaryService(Scheduler scheduler, DictionaryRepository dictionaryRepository) {
        return new DictionaryDomainService(dictionaryRetrievers(dictionaryRepository), scheduler);
    }

    private Set<DictionaryRetriever> dictionaryRetrievers(DictionaryRepository dictionaryRepository) {
        return domainConfiguration.dictionaryRetrievers(wordsRetrievers, dictionaryRepository);
    }
}
