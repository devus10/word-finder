package com.pakisoft.wordfinder.infrastructure.dictionary;

import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class TextFileReader implements FileReader {

    public Set<String> readLines(String fileLocation) {
        var dictionaryFilePath = getFilePath(fileLocation);
        return readLines(dictionaryFilePath);
//                .stream()
//                .map(this::splitLineToStrings)
//                .flatMap(Collection::stream)
//                .collect(Collectors.toCollection(TreeSet::new));
    }

//    private Set<String> splitLineToStrings(String line) {
//        return Stream.of(split(line))
//                .map(String::trim)
//                .collect(Collectors.toSet());
//    }
//
//    private String[] split(String line) {
//        return line.split(DELIMITER);
//    }

    private Set<String> readLines(Path dictionaryFilePath) {
        try (var reader = Files.newBufferedReader(dictionaryFilePath)) {
            return reader.lines().collect(Collectors.toSet());
        } catch (IOException e) {
            throw new IllegalStateException("Failed to read the file lines", e);
        }
    }

    private Path getFilePath(String fileLocation) {
        try {
            return ResourceUtils.getFile(fileLocation).toPath();
        } catch (FileNotFoundException e) {
            throw new IllegalStateException("Cannot find dictionary text file", e);
        }
    }
}
