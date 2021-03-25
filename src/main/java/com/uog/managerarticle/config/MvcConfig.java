package com.uog.managerarticle.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        Path imageDir = Paths.get("./file-article");
        String uploadPath = imageDir.toFile().getAbsolutePath();
        registry.addResourceHandler("/file-article/**").addResourceLocations("file:"+uploadPath+"/");

    }
}
