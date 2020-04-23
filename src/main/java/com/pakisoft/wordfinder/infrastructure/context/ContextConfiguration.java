package com.pakisoft.wordfinder.infrastructure.context;

import com.pakisoft.wordfinder.domain.configuration.DomainConfiguration;
import com.pakisoft.wordfinder.domain.dictionary.DictionaryDomainService;
import com.pakisoft.wordfinder.domain.dictionary.DictionaryProcessInitializer;
import com.pakisoft.wordfinder.domain.dictionary.DictionaryRetriever;
import com.pakisoft.wordfinder.domain.port.primary.WordService;
import com.pakisoft.wordfinder.domain.port.secondary.DictionaryRepository;
import com.pakisoft.wordfinder.domain.port.secondary.Scheduler;
import com.pakisoft.wordfinder.domain.port.secondary.WordsRetriever;
import com.pakisoft.wordfinder.domain.word.WordDomainService;
import com.pakisoft.wordfinder.infrastructure.dictionary.InMemoryDictionaryRepository;
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

    @Bean
    public WordService wordService(DictionaryRepository dictionaryRepository) {
        return new WordDomainService(dictionaryRepository);
    }

    @Bean
    public DictionaryRepository dictionaryRepository() {
        return new InMemoryDictionaryRepository();
    }

    @Bean
    public DictionaryDomainService dictionaryService(DictionaryRepository dictionaryRepository) {
        return new DictionaryDomainService(dictionaryRepository, dictionaryRetrievers());
    }

    @Bean
    public DictionaryProcessInitializer dictionaryProcessInitializer(Scheduler scheduler, DictionaryDomainService dictionaryDomainService) {
        return new DictionaryProcessInitializer(scheduler, dictionaryDomainService);
    }

    private Set<DictionaryRetriever> dictionaryRetrievers() {
        return domainConfiguration.dictionaryRetrievers(wordsRetrievers);
    }
}
