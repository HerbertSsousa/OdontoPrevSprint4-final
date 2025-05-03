package com.example.sinistros.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Permite acesso direto aos endpoints do actuator sem redirecionar para home.html
        registry.addResourceHandler("/actuator/**")
                .addResourceLocations("classpath:/META-INF/resources/");
    }
}
