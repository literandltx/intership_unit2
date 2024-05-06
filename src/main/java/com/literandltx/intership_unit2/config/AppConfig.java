package com.literandltx.intership_unit2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.fasterxml.jackson.core.JsonFactory;

@Configuration
public class AppConfig {
    
    @Bean
    public JsonFactory jsonFactory() {
        return new JsonFactory();
    }

}
