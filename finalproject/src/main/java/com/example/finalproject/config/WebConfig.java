package com.example.finalproject.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
                .addMapping("/**")
                .allowedHeaders("*")
                .allowedOrigins("*")
                .allowedMethods("*");
    }
    @Override   //나중에 리액트 폴더 위치 바뀌면 수정해줘야함
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("file:/home/t24104/v0.5src/web/frontend/build/");

        // AI 모델 이미지 폴더 위치
        registry.addResourceHandler("/ai/models/**")
                .addResourceLocations("file:/home/t24104/v0.9src/ai/models/");
    }
    
}


//여기는 Nunnull이 추가되어있음 - 학과서버
//package com.example.finalproject.config;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.lang.NonNull;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@Configuration
//@RequiredArgsConstructor
//public class WebConfig implements WebMvcConfigurer {
//
//    @Override
//    public void addCorsMappings(@NonNull CorsRegistry registry) {
//        registry
//                .addMapping("/**")
//                .allowedHeaders("*")
//                .allowedOrigins("*")
//                .allowedMethods("*");
//    }
//
//    @Override
//    public void addResourceHandlers(@NonNull ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/**")
//                .addResourceLocations("file:/home/t24104/v0.5src/web/frontend/build/");
//
//        // AI 모델 이미지 폴더 위치
//        registry.addResourceHandler("/ai/models/**")
//                .addResourceLocations("file:/home/t24104/v0.9src/ai/models/");
//    }
//}
