package com.pakisoft.wordfinder.domain.configuration;

import com.pakisoft.wordfinder.domain.dictionary.DictionaryRetriever;
import com.pakisoft.wordfinder.domain.dictionary.Language;
import com.pakisoft.wordfinder.domain.port.secondary.DictionaryRepository;
import com.pakisoft.wordfinder.domain.port.secondary.WordsRetriever;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import java.lang.reflect.Constructor;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DomainConfiguration {

    public static DomainConfiguration getInstance() {
        return SingletonHelper.INSTANCE;
    }

    public Set<DictionaryRetriever> dictionaryRetrievers(Set<WordsRetriever> wordsRetrievers, DictionaryRepository dictionaryRepository) {
        return findDictionaryRetrieversClasses().stream()
                .map(Class::getConstructors)
                .map(Stream::of)
                .map(constructors -> constructors.findAny().orElseThrow())
                .map(constructor -> instantiateDictionaryRetriever(wordsRetrievers, dictionaryRepository, constructor))
                .collect(Collectors.toSet());
    }

    private DictionaryRetriever instantiateDictionaryRetriever(Set<WordsRetriever> wordsRetrievers, DictionaryRepository dictionaryRepository, Constructor<?> constructor) {
        try {
            var dictionaryRetriever = (DictionaryRetriever) constructor.newInstance();
            var matchingWordsRetriever = findMatchingWordsRetriever(wordsRetrievers, dictionaryRetriever.getLanguage());
            dictionaryRetriever.initializeWordsRetriever(matchingWordsRetriever, dictionaryRepository);
            return dictionaryRetriever;
        } catch (Exception e) {
            throw new DomainInitializationError("Cannot instantiate dictionary retriever");
        }
    }

    private Set<Class<? extends DictionaryRetriever>> findDictionaryRetrieversClasses() {
        return reflectionsForDictionaryRetrievers().getSubTypesOf(DictionaryRetriever.class);
    }

    private Reflections reflectionsForDictionaryRetrievers() {
        return new Reflections(getPackageOfDictionaryRetriever(), new SubTypesScanner(true));
    }

    private String getPackageOfDictionaryRetriever() {
        return DictionaryRetriever.class.getPackageName();
    }

    private WordsRetriever findMatchingWordsRetriever(Set<WordsRetriever> wordsRetrievers, Language language) {
        return wordsRetrievers.stream()
                .filter(wordsRetriever -> wordsRetriever.getLanguage().equals(language))
                .collect(oneRetriever(language));
    }

    private Collector<WordsRetriever, ?, WordsRetriever> oneRetriever(Language language) {
        return Collectors.collectingAndThen(
                Collectors.toList(),
                list -> {
                    if (list.isEmpty())
                        throw new DomainInitializationError(s("Unable to find words retriever for %s language", language));
                    if (list.size() > 1)
                        throw new DomainInitializationError(s("There can be only one words retriever for %s langage, found: %s", language, list));
                    return list.get(0);
                }
        );
    }

    private String s(String message, Object... args) {
        return String.format(message, args);
    }

    private static class SingletonHelper {
        private static final DomainConfiguration INSTANCE = new DomainConfiguration();
    }

    private static class DomainInitializationError extends Error {

        DomainInitializationError(String message) {
            super(message);
        }
    }
}
