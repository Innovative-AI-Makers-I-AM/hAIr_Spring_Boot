package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // 이 클래스가 Spring 설정 클래스임을 나타냄
@EnableWebSecurity // Spring Security 설정을 활성화
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // 비밀번호를 해싱하는 데 사용되는 BCryptPasswordEncoder를 빈으로 등록
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.csrf((csrf) -> csrf.disable()); // CSRF 보호를 비활성화
        // 백엔드에서의 경로
        http.authorizeHttpRequests((authorize) ->
                authorize.requestMatchers("/**").permitAll() // 모든 요청 경로에 대해 접근을 허용
        );
        // 프론트에서의 경로
        http.formLogin((formLogin) -> formLogin.loginPage("/login") // 사용자 정의 로그인 페이지를 /login으로 설정 ()안에는 따로 설정 가능
                        // 인증되지 않은 사용자가 보호된 페이지로 접근할 때 로그인 페이지로 유도합니다.
                        .defaultSuccessUrl("/") // 로그인 성공 시 리다이렉트할 URL을 /로 설정
//                .failureUrl("/fail") // 로그인 실패 시 리다이렉트할 URL을 /fail로 설정 (현재 주석 처리됨)
        );
        // 백엔드에서의 경로
        http.logout(logout -> logout.logoutUrl("/logout")); // 로그아웃 URL을 /logout으로 설정
        return http.build(); // 설정된 SecurityFilterChain을 반환
    }
}
