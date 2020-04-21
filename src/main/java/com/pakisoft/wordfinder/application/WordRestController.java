package com.pakisoft.wordfinder.application;

import com.pakisoft.wordfinder.domain.port.primary.WordService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("words")
@RequiredArgsConstructor
public class WordRestController {

    private final WordService wordService;

    @CrossOrigin
    @GetMapping("/{language}:{string}")
    private WordView getWord(@PathVariable String language, @PathVariable String string) {
        return WordView.from(wordService.find(string, language));
    }
}
