package com.pakisoft.wordfinder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class WordFinderApplication {

	public static void main(String[] args) {
		SpringApplication.run(WordFinderApplication.class, args);
	}

}
