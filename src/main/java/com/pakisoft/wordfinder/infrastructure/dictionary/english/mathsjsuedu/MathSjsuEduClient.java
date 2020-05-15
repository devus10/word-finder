package com.pakisoft.wordfinder.infrastructure.dictionary.english.mathsjsuedu;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@FeignClient(name = "math.sjsu.edu", url = "${dictionary.english.math-sjsu-edu.url}", decode404 = true)
public interface MathSjsuEduClient {

    @GetMapping
    Optional<String> getWords();
}
