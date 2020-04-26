package com.pakisoft.wordfinder.application.dictionary;

import com.pakisoft.wordfinder.domain.dictionary.Language;
import lombok.Value;
import org.springframework.util.StringUtils;

@Value
class DictionaryLanguageView {

    String name;
    String code;

    static DictionaryLanguageView from(Language language) {
        return new DictionaryLanguageView(language);
    }

    private DictionaryLanguageView(Language language) {
        this.name = StringUtils.capitalize(language.name().toLowerCase());
        this.code = language.getCode();
    }
}
