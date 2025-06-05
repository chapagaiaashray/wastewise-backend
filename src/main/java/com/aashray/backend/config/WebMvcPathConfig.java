package com.aashray.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.util.pattern.PathPatternParser;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcPathConfig implements WebMvcConfigurer {
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        // ✅ Use the new-style pattern parser in Spring Boot 3+
        configurer.setPatternParser(new PathPatternParser());
    }
}
