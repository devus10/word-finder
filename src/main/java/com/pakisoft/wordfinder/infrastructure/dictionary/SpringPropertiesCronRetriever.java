package com.pakisoft.wordfinder.infrastructure.dictionary;

import com.pakisoft.wordfinder.domain.port.secondary.CronRetriever;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class SpringPropertiesCronRetriever implements CronRetriever {

    @Value("${dictionary-saving-cron}")
    private String dictionarySavingCron;
}
