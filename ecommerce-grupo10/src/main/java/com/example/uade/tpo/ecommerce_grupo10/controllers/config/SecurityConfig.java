package com.example.uade.tpo.ecommerce_grupo10.controllers.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

import com.example.uade.tpo.ecommerce_grupo10.controllers.auth.JwtAuthFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(req -> req
                        // Endpoints publicos
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/productos-publicos/**").permitAll()

                        // Endpoints específicos para COMPRADORES
                        .requestMatchers("/usuarios/*/wishlist/**").hasRole("COMPRADOR")
                        .requestMatchers("/usuarios/*/carrito/**").hasRole("COMPRADOR")

                        // Otros endpoints para COMPRADORES
                        .requestMatchers("/carritos/**").hasRole("COMPRADOR")
                        .requestMatchers("/checkout/**").hasRole("COMPRADOR")
                        .requestMatchers("/wishlists/**").hasRole("COMPRADOR")
                        .requestMatchers("/items-carrito/**").hasRole("COMPRADOR")

                        // Endpoints solo para VENDEDORES
                        .requestMatchers("/productos/**").hasAnyRole("VENDEDOR", "ADMIN")
                        .requestMatchers("/categorias/**").hasAnyRole("VENDEDOR", "ADMIN")
                        .requestMatchers("/descuentos/**").hasAnyRole("VENDEDOR", "ADMIN")
                        .requestMatchers("/imagenes/**").hasAnyRole("VENDEDOR", "ADMIN")

                        // Endpoints de órdenes con reglas específicas
                        .requestMatchers("/ordenes/usuario/**").hasAnyRole("COMPRADOR", "ADMIN") // COMPRADOR puede ver
                                                                                                 // sus órdenes,
                                                                                                 // validación adicional
                                                                                                 // en el controlador
                        .requestMatchers("/ordenes/**").hasRole("ADMIN") // Solo ADMIN puede ver todas las órdenes y
                                                                         // gestionar órdenes

                        // Endpoints solo para ADMIN (debe ir DESPUÉS de los específicos)
                        .requestMatchers("/usuarios/**").hasRole("ADMIN")
                        .requestMatchers("/admin/**").hasRole("ADMIN")

                        .anyRequest().authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOriginPattern("*"); // Permite todos los orígenes para desarrollo
        configuration.addAllowedMethod("*"); // Permite todos los métodos HTTP (GET, POST, PUT, DELETE, etc.)
        configuration.addAllowedHeader("*"); // Permite todos los headers
        configuration.setAllowCredentials(true); // Permite envío de credenciales

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
