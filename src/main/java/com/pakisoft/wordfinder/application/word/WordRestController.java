package com.pakisoft.wordfinder.application.word;

import com.pakisoft.wordfinder.domain.port.primary.WordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("words")
@RequiredArgsConstructor
@Api(value = "Words Rest API")
public class WordRestController {

    private final WordService wordService;

    @CrossOrigin
    @GetMapping("/{language}:{string}")
    @ApiOperation(value = "Get word with its anagrams")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successfully retrieved the word")
    })
    public WordView getWord(
            @ApiParam(value = "dictionary language", required = true) @PathVariable String language,
            @ApiParam(value = "string to search for in dictionary", required = true) @PathVariable String string
    ) {
        return WordView.from(wordService.find(string, language));
    }
}
