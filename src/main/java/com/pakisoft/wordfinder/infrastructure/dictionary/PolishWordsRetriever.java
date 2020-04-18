package com.pakisoft.wordfinder.infrastructure.dictionary;

import com.pakisoft.wordfinder.domain.dictionary.DictionaryLanguage;
import com.pakisoft.wordfinder.domain.port.secondary.FailedWordsRetrievingException;
import com.pakisoft.wordfinder.domain.port.secondary.WordsRetriever;
import lombok.RequiredArgsConstructor;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class PolishWordsRetriever implements WordsRetriever {

    private final static String WORDS_DELIMITER = ",";
    private final static String DICTIONARY_FILE_LOCATION = "odm.txt";

    private final FileReader fileReader;

    @Value("${dictionary.polish.url}")
    private String dictionaryUrl;
    private String downloadedZipFileName;

    @Override
    public Set<String> getWords() throws FailedWordsRetrievingException {
        try {
            downloadedZipFileName = getDictionaryFileName();
            downloadAndSaveFile();
            extractDictionaryFileFromZip();
            return readWordsFromExtractedDictionaryFile();
        } catch (Exception e) {
            throw new FailedWordsRetrievingException("Failed to retrieve words", e);
        } finally {
            removeZipFile();
        }
    }

    @Override
    public DictionaryLanguage getLanguage() {
        return DictionaryLanguage.POLISH;
    }

    private Set<String> readWordsFromExtractedDictionaryFile() {
        return fileReader.readLines(DICTIONARY_FILE_LOCATION).stream()
                .map(this::splitLineToStrings)
                .flatMap(Collection::stream)
                .collect(Collectors.toCollection(TreeSet::new));
    }

    private Set<String> splitLineToStrings(String line) {
        return Stream.of(split(line))
                .map(String::trim)
                .collect(Collectors.toSet());
    }

    private String[] split(String line) {
        return line.split(WORDS_DELIMITER);
    }

    private void extractDictionaryFileFromZip() throws ZipException {
        ZipFile zipFile = new ZipFile(downloadedZipFileName);
        zipFile.extractFile(DICTIONARY_FILE_LOCATION, ".");
    }

    public void downloadAndSaveFile() throws IOException {
        String zipFileUrl = dictionaryUrl + downloadedZipFileName;
        try (InputStream in = URI.create(zipFileUrl).toURL().openStream()) {
            Files.copy(in, Paths.get(downloadedZipFileName));
        }
    }

    public String getDictionaryFileName() throws IOException, NoSuchElementException {
        return Jsoup.connect(dictionaryUrl)
                .get()
                .getElementsByAttributeValueEnding("href", ".zip").stream()
                .findAny()
                .map(Element::attributes)
                .map(attributes -> attributes.get("href"))
                .orElseThrow(() -> new NoSuchElementException("Cannot find web element containg the file name"));
    }

    private void removeZipFile() {
     //TODO: removing the zip file
    }
}
