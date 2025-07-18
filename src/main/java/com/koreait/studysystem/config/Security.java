package com.koreait.studysystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class Security {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) //보호기능을 끄고 시작
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/user/register", "/login").permitAll() //누구나 접근 가능
                        .anyRequest().authenticated() // 외 모든 기능은 로그인 한사람만 가능
                )
                .formLogin(form -> form
                        .loginPage("/login").permitAll()  //로그인 안햇으면 로그인 페이지로 자동으로 가기
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login")   //로그아웃 하면 로그인 페이지로 가기
                );

        return http.build();
    }



    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  //BCryptPasswordEncoder를 Bean으로 등록
    }
}
