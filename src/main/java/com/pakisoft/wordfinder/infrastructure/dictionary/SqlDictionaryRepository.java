package com.pakisoft.wordfinder.infrastructure.dictionary;

import com.google.common.collect.Iterables;
import com.pakisoft.wordfinder.domain.dictionary.Language;
import com.pakisoft.wordfinder.domain.port.secondary.DictionaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@RequiredArgsConstructor
public class SqlDictionaryRepository implements DictionaryRepository {

    private final SystemUtil systemUtil;
    private final List<com.pakisoft.wordfinder.infrastructure.dictionary.Dictionary> dictionaries;

    @Override
    public void save(Dictionary dictionary) {
        var processorsCount = systemUtil.getAvailableProcessors();
        ExecutorService executorService = Executors.newFixedThreadPool(processorsCount);
        var dict = findDictionaryBy(dictionary.getLanguage());
        Iterable<List<String>> setsOfWords = Iterables.partition(dictionary.getWords(), processorsCount);
        setsOfWords.forEach(set -> {
            executorService.execute(() -> save(set, dict));

            //indeksy, na pewno na sorted word, moze nowa kolumna z litera i indeks na nia? powinno przyspieszyc exists
        });
        executorService.shutdown();
    }

    private void save(List<String> words, com.pakisoft.wordfinder.infrastructure.dictionary.Dictionary dictionary) {
        words.forEach(word -> {
            if (!dictionary.exists(word)) {
                dictionary.add(word);
            }
        });
    }

    private com.pakisoft.wordfinder.infrastructure.dictionary.Dictionary findDictionaryBy(Language language) {
        return dictionaries.stream()
                .filter(dao -> dao.applicable(language))
                .findFirst()
                .orElseThrow();
    }
}
