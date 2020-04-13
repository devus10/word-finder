package com.pakisoft.wordfinder.infrastructure.word;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.util.ResourceUtils.CLASSPATH_URL_PREFIX;

@Component
public class ResourcesTextFileDictionaryReader implements DictionaryReader {

    private final static String DELIMITER = ",";

    @Value("${dictionary-text-file}")
    private String dictionaryTextFileName;

    @Override
    public Set<String> readWords() {
        return getFileLines().stream()
                .map(this::splitLineToStrings)
                .flatMap(Collection::stream)
                .collect(Collectors.toCollection(TreeSet::new));
    }

    private Set<String> splitLineToStrings(String line) {
        return Stream.of(split(line))
                .map(String::toLowerCase)
                .map(String::trim)
                .collect(Collectors.toSet());
    }

    private String[] split(String line) {
        return line.split(DELIMITER);
    }

    private Set<String> getFileLines() {
        var dictionaryFilePath = getFilePath();
        return readLines(dictionaryFilePath);
    }

    private Set<String> readLines(Path dictionaryFilePath) {
        try (var reader = Files.newBufferedReader(dictionaryFilePath)) {
            return reader.lines().collect(Collectors.toSet());
        } catch (IOException e) {
            throw new IllegalStateException("Failed to read the file lines", e);
        }
    }

    private Path getFilePath() {
        try {
            return ResourceUtils.getFile(CLASSPATH_URL_PREFIX + dictionaryTextFileName).toPath();
        } catch (FileNotFoundException e) {
            throw new IllegalStateException("Cannot find dictionary text file", e);
        }
    }
}
