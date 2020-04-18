package com.pakisoft.wordfinder.domain.dictionary;

import com.pakisoft.wordfinder.domain.port.secondary.DictionaryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

@RequiredArgsConstructor
@Slf4j
public class DictionaryDomainService {

    private final DictionaryRepository dictionaryRepository;
    private final Set<DictionaryRetriever> dictionaryRetrievers;

    @PostConstruct
    public void saveDictionaries() {
        dictionaryRetrievers.forEach(onRead());
    }

    private Consumer<DictionaryRetriever> onRead() {
        return dictionaryRetriever -> {
            getDictionary(dictionaryRetriever)
                    .ifPresentOrElse(onSuccessfullyRetrievedDictionary(), onFailedToRetrieveDictionary(dictionaryRetriever.getLanguage()));
        };
    }

    private Consumer<Dictionary> onSuccessfullyRetrievedDictionary() {
        return dictionary -> dictionaryRepository.save(Dictionary.withStringsWithAssembledWordsDictionary(dictionary));
    }

    private Runnable onFailedToRetrieveDictionary(DictionaryLanguage language) {
        return () -> log.info("Dictionary not persisted - Failed to retrieve {} dictionary", language);
    }

    private Optional<Dictionary> getDictionary(DictionaryRetriever dictionaryRetriever) {
        try {
            return Optional.of(dictionaryRetriever.getDictionary());
        } catch (DictionaryException e) {
            return Optional.empty();
        }
    }
}
