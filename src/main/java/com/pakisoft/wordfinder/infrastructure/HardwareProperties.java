package com.pakisoft.wordfinder.infrastructure;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "hardware")
@Getter
@Setter
public class HardwareProperties {

    private int cpus;
}
