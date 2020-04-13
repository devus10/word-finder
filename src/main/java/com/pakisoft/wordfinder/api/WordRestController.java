package com.pakisoft.wordfinder.api;

import com.pakisoft.wordfinder.domain.word.Word;
import com.pakisoft.wordfinder.domain.word.WordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("words")
@RequiredArgsConstructor
@Slf4j
public class WordRestController {

    private final WordService wordService;

    @GetMapping("/{word}")
    private Word getWord(@PathVariable String word) {
        return wordService.find(word);
    }

    @GetMapping("/{word}/perms")
    private Word getWordWith(@PathVariable String word) {
        StopWatch watch = new StopWatch();
        watch.start();
        var x = wordService.assemblyWordFromString(word);
        watch.stop();
        log.info("Elapsed time: " + watch.getLastTaskInfo().getTimeSeconds());
        return x;
    }
}
