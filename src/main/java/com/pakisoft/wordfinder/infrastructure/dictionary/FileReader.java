package com.pakisoft.wordfinder.infrastructure.dictionary;

import java.util.Set;

public interface FileReader {

    Set<String> readLines(String fileLocation);
}
