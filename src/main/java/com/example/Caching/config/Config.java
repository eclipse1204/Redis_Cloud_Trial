package com.example.Caching.config;

import org.modelmapper.ModelMapper;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class Config {

    @Bean
    public ModelMapper modelMapper() {
       return new ModelMapper();
    }
}
