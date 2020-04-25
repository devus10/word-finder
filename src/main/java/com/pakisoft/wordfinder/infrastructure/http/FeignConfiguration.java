package com.pakisoft.wordfinder.infrastructure.http;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.pakisoft.wordfinder.infrastructure")
public class FeignConfiguration {
}
