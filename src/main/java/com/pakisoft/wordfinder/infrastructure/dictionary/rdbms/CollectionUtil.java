package com.pakisoft.wordfinder.infrastructure.dictionary.rdbms;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

class CollectionUtil {

    static <T> List<ArrayList<T>> partition(Collection<T> collection, int numberOfPartitions) {
        if (numberOfPartitions < 1) {
            throw new IllegalArgumentException("There must be at least 1 partition");
        }
        if (collection.isEmpty()) {
            return Collections.emptyList();
        }

        int partitionSize = collection.size() / numberOfPartitions;
        if (partitionSize < 1) {
            throw new IllegalStateException("Collection size cannot be smaller than number of partitions");
        }

        var list = Lists.newArrayList(Iterables.partition(collection, partitionSize))
                .stream()
                .map(ArrayList::new)
                .collect(Collectors.toCollection(LinkedList::new));

        if (list.size() > numberOfPartitions) {
            list.getFirst().addAll(list.getLast());
            list.remove(list.getLast());
        }

        return list;
    }

}
