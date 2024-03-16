//package com.filmbook.config.przeszlosc;
//
//import com.filmbook.config.UserAuthProvider;
//import com.filmbook.config.UserAuthenticationEntryPoint;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
//@RequiredArgsConstructor
//public class WebSecurityConfig {
//    @Autowired
//    private final UserAuthenticationEntryPoint userAuthenticationEntryPoint;
//    @Autowired
//    private final UserAuthProvider userAuthProvider;
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
////                .exceptionHandling().authenticationEntryPoint(userAuthenticationEntryPoint)
////                .and()
////                .addFilterBefore(new JwtAuthFilter(userAuthProvider), BasicAuthenticationFilter.class)
////                .csrf().disable()
////                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
////                .and()
//                .authorizeHttpRequests(authorize -> authorize
//                        // dostęp zawsze
//                        .requestMatchers(HttpMethod.POST, "/login", "/register").permitAll()
////                        .requestMatchers(HttpMethod.POST, "/**").permitAll()
//                        // dostęp jak jesteś zalogowany
//                        .anyRequest().permitAll()
//                );
////                .formLogin(form -> form
////                        .loginPage("/login")
////                        .failureUrl("/logina?failed")
////                        .loginProcessingUrl("/login/process")
////                        .defaultSuccessUrl("/home")
////                );
//
//        return http.build();
//
//    }
//
//}
//
//
