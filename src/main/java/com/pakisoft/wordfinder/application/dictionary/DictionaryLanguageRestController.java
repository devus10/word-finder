package com.pakisoft.wordfinder.application.dictionary;

import com.pakisoft.wordfinder.domain.port.primary.DictionaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("dictionary-languages")
@RequiredArgsConstructor
@CrossOrigin
public class DictionaryLanguageRestController {

    private final DictionaryService dictionaryService;

    @GetMapping
    public Set<DictionaryLanguageView> getSupportedLanguages() {
        return dictionaryService.getSupportedLanguages().stream()
                .map(DictionaryLanguageView::from)
                .collect(Collectors.toSet());
    }
}
