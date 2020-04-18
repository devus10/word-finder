package com.pakisoft.wordfinder.domain.port.primary;

import com.pakisoft.wordfinder.domain.word.Word;

public interface WordService {

    Word find(String string, String language);
}
