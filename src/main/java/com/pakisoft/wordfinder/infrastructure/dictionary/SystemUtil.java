package com.pakisoft.wordfinder.infrastructure.dictionary;

import org.springframework.stereotype.Component;

@Component
public class SystemUtil {

    public int getAvailableProcessors() {
        return Runtime.getRuntime().availableProcessors();
    }
}
