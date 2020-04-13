package com.pakisoft.wordfinder.domain.word;

public interface WordService {

    Word find(String string);

    Word assemblyWordFromString(String string);
}
