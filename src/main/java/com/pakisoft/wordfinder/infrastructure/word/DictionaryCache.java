package com.pakisoft.wordfinder.infrastructure.word;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

@Component
@Getter
class DictionaryCache {

    private Set<String> cache;
    private List<Set<String>> subsetsCache;
    private Map<String, Set<String>> map;

    public boolean isEmpty() {
        return cache == null || cache.isEmpty();
    }

    public void init(Set<String> dictionary) {
        if (cache == null) {
            cache = new TreeSet<>(dictionary);
        }
    }

    public void initSubsetsCache(List<Set<String>> sbcache) {
        if (subsetsCache == null) {
            subsetsCache = sbcache;
        }
    }

    public boolean isSubsetCacheEmpty() {
        return subsetsCache == null || subsetsCache.isEmpty();
    }

    public void initMap(Map<String, Set<String>> sbcache) {
        if (map == null) {
            map = sbcache;
        }
    }

    public boolean isMapEmpty() {
        return map == null || map.isEmpty();
    }
}
