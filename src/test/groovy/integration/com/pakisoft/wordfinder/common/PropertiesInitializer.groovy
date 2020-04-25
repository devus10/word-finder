package com.pakisoft.wordfinder.common

import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.test.context.support.TestPropertySourceUtils

class PropertiesInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    public static List<String> properties

    @Override
    void initialize(ConfigurableApplicationContext applicationContext) {
        TestPropertySourceUtils.addInlinedPropertiesToEnvironment(
                applicationContext,
                properties as String[]
        )
    }
}