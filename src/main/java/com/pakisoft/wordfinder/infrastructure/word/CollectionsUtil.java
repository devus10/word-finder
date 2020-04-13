package com.pakisoft.wordfinder.infrastructure.word;

import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Component
public class CollectionsUtil {

    public static <T> List<Set<T>> divideIntoSubsets(Collection<T> collection, int numberOfSubsets) {
        List<T> list = new ArrayList<>(collection);
        int subsetSize = list.size() / numberOfSubsets;
        List<Set<T>> subsets = createSubsets(numberOfSubsets);

        int offset = 0;
        int limit = subsetSize;
        int setIndex = 0;
        while (setIndex < numberOfSubsets) {
            for (int j = offset; j < limit; j++) {
                subsets.get(setIndex).add(list.get(j));
            }

            offset = limit;
            limit += subsetSize;

            setIndex++;
        }

        if (offset < list.size()) {
            int numberOfLeftElements = list.size() - offset;
            int index = numberOfLeftElements;
            for (int i = offset; i < list.size(); i++) {
                subsets.get(numberOfLeftElements - index).add(list.get(i));
                index--;
            }
        }

        return subsets;
    }

    private static <T> List<Set<T>> createSubsets(int numberOfSubsets) {
        List<Set<T>> subsets = new ArrayList<>();

        for (int i = 0; i < numberOfSubsets; i++) {
            subsets.add(new HashSet<>());
        }

        return subsets;
    }

    public static Map<Character, Set<String>> createDictionaryMap(Set<String> dictionary) {
        return dictionary.stream()
                .collect(Collectors.groupingBy(s -> s.charAt(0), Collectors.toSet()));
    }

    public static <T> List<List<T>> divideIntoSubsetsGroupingBy(Collection<T> collection, int numberOfSubsets) {
        AtomicInteger number = new AtomicInteger();
        return new ArrayList<>(collection.stream().collect(Collectors.groupingBy(t -> number.getAndIncrement() / numberOfSubsets)).values());
    }
}
