package com.pakisoft.wordfinder.infrastructure.dictionary.polish.sjp;

import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class TextFileReader implements FileReader {

    public Set<String> readLines(String fileLocation) {
        var dictionaryFilePath = getFilePath(fileLocation);
        return readLines(dictionaryFilePath);
    }

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
