package com.pakisoft.wordfinder.infrastructure.dictionary.polish.sjp;

import com.google.common.collect.ImmutableSet;
import com.pakisoft.wordfinder.infrastructure.dictionary.polish.PolishWordsRetriever;
import lombok.RequiredArgsConstructor;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.openhft.chronicle.map.ChronicleMap;
import net.openhft.chronicle.set.ChronicleSet;
import org.eclipse.collections.api.factory.Sets;
import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.impl.set.mutable.UnifiedSet;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class SjpPolishWordsRetriever implements PolishWordsRetriever {

    private final static String WORDS_DELIMITER = ",";
    private final static String DICTIONARY_FILE_NAME = "odm.txt";

    private Logger log = LoggerFactory.getLogger(SjpPolishWordsRetriever.class);

    private final FileReader fileReader;
    private final HtmlDocumentFetcher htmlDocumentFetcher;
    private final FileUtil fileUtil;

    @Value("${dictionary.polish.sjp.url}")
    private String dictionaryUrl;
    @Value("${dictionary.polish.target-dir}")
    private String targetDirectory;
    private String targetZipFilePath;
    private String extractedDictionaryFilePath;
    private String sjpZipFileName;

    @Override
    public Set<String> getWords() throws FailedWordsRetrievingException {
        try {
            createTargetDirectoryIfNotExists();
            scrapZipFileNameFromSjpWebPage();
            setTargetZipFilePath();
            setExtractedDictionaryFilePath();
            downloadAndExtractDictionaryFileIfNecessary();
            return readWordsFromExtractedDictionaryFile();
        } catch (Exception e) {
            log.error(e.toString());
            throw new FailedWordsRetrievingException("Failed to retrieve words", e);
        }
    }

    private void createTargetDirectoryIfNotExists() throws IOException {
        Files.createDirectories(Paths.get(targetDirectory));
    }

    private void downloadAndExtractDictionaryFileIfNecessary() throws IOException {
        if (!zipFileAlreadyExists()) {
            downloadAndSaveFile();
            extractDictionaryFileFromZip();
        } else {
            log.info("File downloading not required. {} file already exists.", targetZipFilePath);
        }
    }

    private void setExtractedDictionaryFilePath() {
        extractedDictionaryFilePath = targetDirectory + "/" + DICTIONARY_FILE_NAME;
    }

    private void setTargetZipFilePath() {
        targetZipFilePath = targetDirectory + "/" + sjpZipFileName;
    }

    private boolean zipFileAlreadyExists() {
        try {
            return ResourceUtils.getFile(targetZipFilePath).exists();
        } catch (FileNotFoundException e) {
            return false;
        }
    }

    private Set<String> readWordsFromExtractedDictionaryFile() {
//        ChronicleSet<String> words = null;
//        try {
//            words = ChronicleSet
//                    .of(String.class)
//                    .name("polish_dict")
//                    .averageKey("blendziorowaty")
//                    .entries(5_000_000)
//                    .createPersistedTo(new File("words.dat"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//        UnifiedSet<String> set = new UnifiedSet<>();

        return fileReader.readLines(extractedDictionaryFilePath).stream()
                .map(this::splitLineToStrings)
                .flatMap(Collection::stream)
//                .forEach(set::add);
//        return set;
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
        new ZipFile(targetZipFilePath).extractFile(DICTIONARY_FILE_NAME, targetDirectory);
    }

    private void downloadAndSaveFile() throws IOException {
        fileUtil.downloadAndSaveFile(getSjpZipFileUrl(), targetZipFilePath);
    }

    private String getSjpZipFileUrl() {
        return dictionaryUrl + "/" + sjpZipFileName;
    }

    private void scrapZipFileNameFromSjpWebPage() throws IOException, NoSuchElementException {
        sjpZipFileName = htmlDocumentFetcher.getDocument(dictionaryUrl)
                .getElementsByAttributeValueEnding("href", ".zip").stream()
                .findAny()
                .map(Element::attributes)
                .map(attributes -> attributes.get("href"))
                .orElseThrow(() -> new NoSuchElementException("Cannot find web element containing the file name"));
    }
}
