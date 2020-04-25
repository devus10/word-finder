package com.pakisoft.wordfinder.infrastructure.dictionary.english.mathsjsedu;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "mathsjsuedu", url = "http://www.math.sjsu.edu/~foster/dictionary.txt")
public interface MathSjsuEduClient {

    @RequestMapping(method = RequestMethod.GET)
    String getWords();
}
