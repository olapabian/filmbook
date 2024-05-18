package com.filmbook.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@Configuration
public class WebConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration configuration = new CorsConfiguration();

        // Ustawienie zezwolenia tylko na http://localhost:5173

        configuration.addAllowedOrigin("http://localhost:5173");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod(HttpMethod.OPTIONS);
        configuration.addAllowedMethod(HttpMethod.PATCH);
        configuration.addAllowedMethod(HttpMethod.GET);
        configuration.addAllowedMethod(HttpMethod.POST);
        configuration.addAllowedMethod(HttpMethod.PUT);
        configuration.addAllowedMethod(HttpMethod.DELETE);
        source.registerCorsConfiguration("/**", configuration);
        // Ustawienie zezwolenia na wszystkie metody (GET, POST, PUT, DELETE, itp.)
        configuration.setAllowedMethods(Arrays.asList(
                HttpMethod.GET.name(),
                HttpMethod.POST.name(),
                HttpMethod.PUT.name(),
                HttpMethod.DELETE.name(),
                HttpMethod.OPTIONS.name(),
                HttpMethod.PATCH.name()
        ));

        // Ustawienie zezwolenia na wszystkie nagłówki
        configuration.setAllowedHeaders(Arrays.asList(
                HttpHeaders.AUTHORIZATION,
                HttpHeaders.CONTENT_TYPE,
                HttpHeaders.ACCEPT
        ));

        // Ustawienie, czy żądania mogą zawierać nagłówki, które są niebezpieczne ze względów bezpieczeństwa
        configuration.setAllowCredentials(true);

        // Ustawienie maksymalnego czasu, przez jaki przeglądarka może przechowywać cache konfiguracji CORS
        configuration.setMaxAge(3600L);

        source.registerCorsConfiguration("/**", configuration);
        return new CorsFilter(source);
    }
}


