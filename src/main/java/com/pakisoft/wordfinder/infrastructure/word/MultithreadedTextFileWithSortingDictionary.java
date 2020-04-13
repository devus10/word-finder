package com.pakisoft.wordfinder.infrastructure.word;

import com.pakisoft.wordfinder.domain.word.Dictionary;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MultithreadedTextFileWithSortingDictionary implements Dictionary {

    private final DictionaryReader dictionaryReader;
    private final DictionaryCache dictionaryCache;

    @Override
    public Optional<String> find(String word) {
        return Optional.empty();
    }

    @Override
    public Set<String> findMatching(String word) {
        if (dictionaryCache.isEmpty()) {
            dictionaryCache.init(dictionaryReader.readWords());
        }

        if (dictionaryCache.isMapEmpty()) {
            dictionaryCache.initMap(DictionaryUtil.createMap(dictionaryCache.getCache()));
        }

        return dictionaryCache.getMap().get(DictionaryUtil.sortedAlphabetically(word));

//
//        if (dictionaryCache.isMapEmpty()) {
//            dictionaryCache.initMap(CollectionsUtil.createDictionaryMap(dictionaryCache.getCache()));
//        }
//
//        return dictionaryCache.getMap().get(word.charAt(0)).parallelStream()
//                .filter(word::equals)
//                .collect(Collectors.toSet());
    }
}
