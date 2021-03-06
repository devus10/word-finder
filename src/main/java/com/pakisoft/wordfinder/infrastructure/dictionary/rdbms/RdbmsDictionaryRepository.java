package com.pakisoft.wordfinder.infrastructure.dictionary.rdbms;

import com.google.common.collect.Lists;
import com.pakisoft.wordfinder.domain.port.secondary.DictionaryRepository;
import com.pakisoft.wordfinder.infrastructure.HardwareProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@RequiredArgsConstructor
public class RdbmsDictionaryRepository implements DictionaryRepository {

    private final HardwareProperties hardwareProperties;
    private final PersistedDictionaryFinder<? extends DictionaryWordEntity> persistedDictionaryFinder;
    private ExecutorService executorService;
    private int threads;

    @Override
    public void save(Dictionary dictionary) {
        threads = hardwareProperties.getCpus();
        executorService = createExecutorService();
        savePartitionedWordsInParallel(dictionary);
        executorService.shutdown();
    }

    private void savePartitionedWordsInParallel(Dictionary dictionary) {
        partitionedDictionaryWords(dictionary, threads).forEach(words ->
            executorService.execute(() -> {
                var persistedDictionary = persistedDictionaryFinder.findBy(dictionary.getLanguage());
                this.save(words, persistedDictionary);
            })
        );
    }

    private Iterable<List<String>> partitionedDictionaryWords(Dictionary dictionary, int partitionSize) {
        return Lists.partition(Lists.newArrayList(dictionary.getWords()), partitionSize);
    }

    private ExecutorService createExecutorService() {
        return Executors.newFixedThreadPool(threads);
    }

    private void save(Collection<String> words, PersistedDictionary<? extends DictionaryWordEntity> persistedDictionary) {
        words.forEach(persistedDictionary::addIfMissing);
    }
}
