package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins(
                            "http://localhost:3000",
                            "https://hair-react-js-git-main-annsunghees-projects.vercel.app/",
                            "https://hair-react-js.vercel.app/"
                        )
                        .allowedMethods("GET", "POST", "PUT", "DELETE");
            }
        };
    }

    @Bean
    public RestTemplate restTemplate() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(50000);
        factory.setReadTimeout(50000);
        return new RestTemplate(factory);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // /simulatedImg/** 경로로 요청이 올 경우, 파일 시스템의 simulatedImg 디렉토리에서 파일을 찾도록 설정
        registry.addResourceHandler("/simulatedImg/**")
                .addResourceLocations("file:simulatedImg/");
    }
}
