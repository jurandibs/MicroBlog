package dev.jurandibs.microblog.config;

import dev.jurandibs.microblog.models.User;
import dev.jurandibs.microblog.repositories.UserRepository;
import dev.jurandibs.microblog.services.AuthenticationService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserRepository userRepository;

    // Lista de caminhos que devem IGNORAR completamente o filtro JWT
    private static final String[] EXCLUDED_PATHS = {
            "/v3/api-docs",
            "/v3/api-docs.yaml",
            "/v3/api-docs.json",
            "/swagger-ui",
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/login",
            "/users/save"
    };

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String path = request.getRequestURI();

        // PULA o filtro inteiro para OpenAPI e Swagger
        if (isExcludedPath(path)) {
            filterChain.doFilter(request, response);
            return;
        }

        // Caso contrário, executa a lógica normal de JWT
        String token = extractToken(request);
        if (token != null) {
            String username = authenticationService.validateJwtToken(token);
            if (username != null) {  // validação extra (caso token inválido)
                User user = userRepository.findByUsername(username);
                if (user != null) {
                    var authenticationToken = new UsernamePasswordAuthenticationToken(
                            user, null, user.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }

        filterChain.doFilter(request, response);
    }

    private String extractToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;
        }
        return authHeader.substring(7); // remove "Bearer "
    }

    // Verifica se o path está na lista de exclusões
    private boolean isExcludedPath(String path) {
        for (String excluded : EXCLUDED_PATHS) {
            if (excluded.endsWith("/**")) {
                String base = excluded.substring(0, excluded.length() - 3);
                if (path.startsWith(base)) {
                    return true;
                }
            } else if (path.equals(excluded) || path.startsWith(excluded + "/")) {
                return true;
            }
        }
        return false;
    }
}