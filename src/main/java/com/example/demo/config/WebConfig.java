package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:3000",
                                                    "https://hair-react-js-git-main-annsunghees-projects.vercel.app/",
                                                    "https://hair-react-js.vercel.app/"
                        ) // React 로컬 개발 서버 주소
                        .allowedMethods("GET", "POST", "PUT", "DELETE");
                // registry.addMapping("/**")
                //         .allowedOrigins("http://localhost:3000") // React 로컬 개발 서버 주소
                //         .allowedOrigins("https://hair-react-js-git-main-annsunghees-projects.vercel.app/") // React Vercel 개발 서버 주소
                //         .allowedOrigins("https://hair-react-js.vercel.app/") // React Vercel 개발 서버 주소
                //         .allowedMethods("GET", "POST", "PUT", "DELETE");
            }
        };
    }
}