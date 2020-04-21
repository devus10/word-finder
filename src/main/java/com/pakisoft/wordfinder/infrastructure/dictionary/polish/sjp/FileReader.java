package com.pakisoft.wordfinder.infrastructure.dictionary.polish.sjp;

import java.util.Set;

public interface FileReader {

    Set<String> readLines(String fileLocation);
}
