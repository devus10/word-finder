package com.pakisoft.wordfinder.infrastructure.context;

import com.pakisoft.wordfinder.domain.word.DomainWordService;
import com.pakisoft.wordfinder.domain.word.Dictionary;
import com.pakisoft.wordfinder.domain.word.WordService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ContextConfiguration {

    @Bean
    public WordService wordService(Dictionary dictionary) {
        return new DomainWordService(dictionary);
    }
}
