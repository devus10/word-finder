package com.pakisoft.wordfinder.domain.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
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
