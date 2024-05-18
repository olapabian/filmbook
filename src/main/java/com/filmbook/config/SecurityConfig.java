package com.filmbook.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserAuthenticationEntryPoint userAuthenticationEntryPoint;
    private final UserAuthProvider userAuthProvider;

    @Autowired
    public SecurityConfig(UserAuthenticationEntryPoint userAuthenticationEntryPoint, UserAuthProvider userAuthProvider) {
        this.userAuthenticationEntryPoint = userAuthenticationEntryPoint;
        this.userAuthProvider = userAuthProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .exceptionHandling().authenticationEntryPoint(userAuthenticationEntryPoint)
                .and()
                .addFilterBefore(new JwtAuthFilter(userAuthProvider), BasicAuthenticationFilter.class)
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()

                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/register").permitAll()
                        .requestMatchers(HttpMethod.GET, "/user").permitAll()
                        .requestMatchers(HttpMethod.GET, "/userInfo").permitAll()
                        .requestMatchers(HttpMethod.GET, "/userInfo/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/userImage/**").permitAll()
//                        .requestMatchers(HttpMethod.POST, "/deleteUserImageById/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/deleteUserImageById/**").authenticated()
//                        .requestMatchers(HttpMethod.PUT, "/deleteUserImageById/**").authenticated()
                        .anyRequest().authenticated()
                );

//        http
//                .csrf().disable()
//                .formLogin().disable()
//                .logout().disable()
//                .authorizeRequests(authorize -> authorize
//                        .requestMatchers(HttpMethod.POST,"/login").permitAll()
//                        .anyRequest().permitAll()
//                );


        return http.build();
    }

    @Bean
    public static PasswordEncoder passwordencoder() {
        return new BCryptPasswordEncoder();
    }


}