package com.pakisoft.wordfinder.infrastructure.dictionary;

import com.pakisoft.wordfinder.infrastructure.dictionary.polish.PolishWordsRetriever;
import com.pakisoft.wordfinder.infrastructure.dictionary.polish.sjp.FileReader;
import com.pakisoft.wordfinder.infrastructure.dictionary.polish.sjp.FileUtil;
import com.pakisoft.wordfinder.infrastructure.dictionary.polish.sjp.HtmlDocumentFetcher;
import com.pakisoft.wordfinder.infrastructure.dictionary.polish.sjp.SjpPolishWordsRetriever;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DictionaryConfiguration {

    @Bean
    public PolishWordsRetriever sjpPolishWordsRetriever(FileReader fileReader, HtmlDocumentFetcher htmlDocumentFetcher, FileUtil fileUtil) {
        return new SjpPolishWordsRetriever(fileReader, htmlDocumentFetcher, fileUtil);
    }

}
