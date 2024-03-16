package com.filmbook.config;

import org.springframework.context.annotation.Bean;


public class PasswordConfig {
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
