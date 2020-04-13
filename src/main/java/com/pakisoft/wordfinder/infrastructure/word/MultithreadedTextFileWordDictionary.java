package com.pakisoft.wordfinder.infrastructure.word;

import com.pakisoft.wordfinder.domain.word.Dictionary;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

//@Component
@RequiredArgsConstructor
public class MultithreadedTextFileWordDictionary implements Dictionary {

    private static final int THREADS_NUMBER = 8;

    private final DictionaryReader dictionaryReader;
    private final DictionaryCache dictionaryCache;

    @Override
    public Optional<String> find(String word) {
        if (dictionaryCache.isEmpty()) {
            dictionaryCache.init(dictionaryReader.readWords());
        }

        return dictionaryCache.getCache().stream()
                .filter(dictionaryWord -> dictionaryWord.equals(word))
                .findAny();
    }

    @Override
    public Set<String> findMatching(String word) {
        if (dictionaryCache.isEmpty()) {
            dictionaryCache.init(dictionaryReader.readWords());
        }

        if(dictionaryCache.isSubsetCacheEmpty()) {
            var dictionary = new ArrayList<>(dictionaryCache.getCache());
            dictionaryCache.initSubsetsCache(CollectionsUtil.divideIntoSubsets(dictionary, THREADS_NUMBER));
        }

        Executor executor = Executors.newFixedThreadPool(THREADS_NUMBER);
        Set<CompletableFuture<Set<String>>> futures = dictionaryCache.getSubsetsCache().stream()
                .map(subset -> CompletableFuture.supplyAsync(() -> {
                    return subset.stream()
                            .filter(dictionaryWord -> dictionaryWord.equals(word))
                            .collect(Collectors.toSet());
                }, executor))
                .collect(Collectors.toSet());


        CompletableFuture<Void> allFutures = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));

        CompletableFuture<Set<Set<String>>> appliedFutures = allFutures.thenApply(v -> {
                    return futures.stream()
                            .map(CompletableFuture::join)
                            .collect(Collectors.toSet());
                });

        return appliedFutures.join().stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }


}
