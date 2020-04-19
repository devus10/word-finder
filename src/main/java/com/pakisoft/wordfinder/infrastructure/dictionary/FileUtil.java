package com.pakisoft.wordfinder.infrastructure.dictionary;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class FileUtil {

    public void downloadAndSaveFile(String url, String targetFilePath) throws IOException {
        try (InputStream in = URI.create(url).toURL().openStream()) {
            Files.copy(in, Paths.get(targetFilePath));
        }
    }
}
