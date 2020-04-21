package com.pakisoft.wordfinder.domain.configuration;

import com.pakisoft.wordfinder.domain.dictionary.DictionaryRetriever;
import com.pakisoft.wordfinder.domain.dictionary.Language;
import com.pakisoft.wordfinder.domain.port.secondary.WordsRetriever;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import java.lang.reflect.Constructor;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DomainConfiguration {

    public static DomainConfiguration getInstance() {
        return SingletonHelper.INSTANCE;
    }

    public Set<DictionaryRetriever> dictionaryRetrievers(Set<WordsRetriever> wordsRetrievers) {
        return findDictionaryRetrieversClasses().stream()
                .map(Class::getConstructors)
                .map(Stream::of)
                .map(constructors -> constructors.findAny().orElseThrow())
                .map(constructor -> instantiateDictionaryRetriever(wordsRetrievers, constructor))
                .collect(Collectors.toSet());
    }

    private DictionaryRetriever instantiateDictionaryRetriever(Set<WordsRetriever> wordsRetrievers, Constructor<?> constructor) {
        try {
            var dictionaryRetriever = (DictionaryRetriever) constructor.newInstance();
            var matchingWordsRetriever = findMatchingWordsRetriever(wordsRetrievers, dictionaryRetriever.getLanguage());
            dictionaryRetriever.initializeWordsRetriever(matchingWordsRetriever);
            return dictionaryRetriever;
        } catch (Exception e) {
            throw new Error("Cannot instantiate dictionary retriever");
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
                .findFirst()
                .orElseThrow(() -> new Error(String.format("Unable to find words retriever for %s language", language)));
    }

    private static class SingletonHelper {
        private static final DomainConfiguration INSTANCE = new DomainConfiguration();
    }
}