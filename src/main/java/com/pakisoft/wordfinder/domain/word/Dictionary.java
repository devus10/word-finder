package com.pakisoft.wordfinder.domain.word;

import java.util.Optional;
import java.util.Set;

public interface Dictionary {

    Optional<String> find(String word);

    Set<String> findMatching(String word);
}
