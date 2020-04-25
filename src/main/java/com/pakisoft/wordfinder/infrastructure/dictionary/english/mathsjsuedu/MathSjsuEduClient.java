package com.pakisoft.wordfinder.infrastructure.dictionary.english.mathsjsuedu;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "math.sjsu.edu", url = "${dictionary.english.math-sjsu-edu.url}")
public interface MathSjsuEduClient {

    @RequestMapping(method = RequestMethod.GET)
    String getWords();
}
