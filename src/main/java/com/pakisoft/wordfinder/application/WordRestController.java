package com.pakisoft.wordfinder.application;

import com.pakisoft.wordfinder.domain.port.primary.WordService;
import com.pakisoft.wordfinder.domain.word.Word;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("words")
@RequiredArgsConstructor
public class WordRestController {

    private final WordService wordService;

    @CrossOrigin
    @GetMapping("/{language}:{string}")
    private Word getWord(@PathVariable String language, @PathVariable String string) {
        return wordService.find(string, language);
    }
}
