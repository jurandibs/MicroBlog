package dev.jurandibs.microblog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SpringSecurityAuthentication {


    @Autowired
    private SecurityFilter securityFilter;

    // ENDPOINTS DO SPRINGDOC OPENAPI (Spring Boot 3+) + Swagger UI
    public static final String[] OPENAPI_PATHS = {
            "/v3/api-docs/**",
            "/v3/api-docs",
            "/v3/api-docs.yaml",
            "/v3/api-docs.json",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/swagger-ui/index.html"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // Libera OpenAPI + Swagger UI (obrigatório no Spring Boot 3)
                        .requestMatchers(OPENAPI_PATHS).permitAll()
                        .requestMatchers(HttpMethod.POST, "/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/users/save").hasRole("ADMIN")

                        // Users
                        .requestMatchers(HttpMethod.GET, "/users/getAll").hasRole("USER")
                        .requestMatchers(HttpMethod.GET, "/users/get").hasRole("USER")
                        .requestMatchers(HttpMethod.POST, "/users/update").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/users/delete").hasRole("ADMIN")

                        // Posts
                        .requestMatchers(HttpMethod.POST, "/posts/save").hasRole("USER")
                        .requestMatchers(HttpMethod.GET, "/posts/getAll").hasRole("USER")
                        .requestMatchers(HttpMethod.GET, "/posts/get").hasRole("USER")
                        .requestMatchers(HttpMethod.POST, "/posts/update").hasRole("USER")
                        .requestMatchers(HttpMethod.DELETE, "/posts/delete").hasRole("USER")

                        // Tags
                        .requestMatchers(HttpMethod.POST, "/tags/save").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/tags/getAll").hasRole("USER")
                        .requestMatchers(HttpMethod.GET, "/tags/get").hasRole("USER")
                        .requestMatchers(HttpMethod.POST, "/tags/update").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/tags/delete").hasRole("ADMIN")

                        // Tudo o mais exige autenticação
                        .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(OPENAPI_PATHS);
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
