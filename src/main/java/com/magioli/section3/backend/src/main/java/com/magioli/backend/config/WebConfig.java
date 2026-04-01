package com.magioli.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ApiVersionConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void configureApiVersioning(ApiVersionConfigurer configurer) {
        WebMvcConfigurer.super.configureApiVersioning(configurer);

//        configurer.usePathSegment(2)
//                .addSupportedVersions("1.0", "2.0", "3.0");

//        configurer.useQueryParam("version")
//                .addSupportedVersions("1.0", "2.0", "3.0")
//                .setDefaultVersion("1.0");

//        configurer.useRequestHeader("X-API-VERSION")
//                .addSupportedVersions("1.0", "2.0", "3.0")
//                .setDefaultVersion("1.0");

        configurer.useMediaTypeParameter(MediaType.parseMediaType("application/vnd.magioli+json"), "v")
                .addSupportedVersions("1.0", "2.0", "3.0")
                .setDefaultVersion("1.0");
    }
}
