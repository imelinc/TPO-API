package com.example.uade.tpo.ecommerce_grupo10.controllers.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.uade.tpo.ecommerce_grupo10.controllers.auth.JwtAuthFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req -> req
                        // Endpoints públicos
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/productos-publicos/**").permitAll()

                        // Endpoints solo para COMPRADORES
                        .requestMatchers("/carritos/**").hasRole("COMPRADOR")
                        .requestMatchers("/checkout/**").hasRole("COMPRADOR")
                        .requestMatchers("/wishlists/**").hasRole("COMPRADOR")

                        // Endpoints solo para VENDEDORES
                        .requestMatchers("/productos/**").hasAnyRole("VENDEDOR", "ADMIN")
                        .requestMatchers("/categorias/**").hasAnyRole("VENDEDOR", "ADMIN")
                        .requestMatchers("/descuentos/**").hasAnyRole("VENDEDOR", "ADMIN")
                        .requestMatchers("/imagenes/**").hasAnyRole("VENDEDOR", "ADMIN")

                        // Endpoints solo para ADMIN
                        .requestMatchers("/usuarios/**").hasRole("ADMIN")

                        // Endpoints compartidos (ambos roles pueden ver órdenes)
                        .requestMatchers("/ordenes/**").authenticated()

                        .anyRequest().authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
