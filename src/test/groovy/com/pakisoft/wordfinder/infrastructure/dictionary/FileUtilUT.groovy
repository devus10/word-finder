package com.pakisoft.wordfinder.infrastructure.dictionary

import spock.lang.Specification

class FileUtilUT extends Specification {

    def "should create directories with a file"() {
        expect:
        FileUtil.createDirectoryIfNotExists("dictionaries/polish", "file.txt")
        1
    }
}
