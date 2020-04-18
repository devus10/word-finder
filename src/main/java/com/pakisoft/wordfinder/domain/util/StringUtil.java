package com.pakisoft.wordfinder.domain.util;

public class StringUtil {

    public static String lowerCasedAndSortedAlphabetically(String string) {
        return string
                .toLowerCase()
                .chars()
                .sorted()
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
