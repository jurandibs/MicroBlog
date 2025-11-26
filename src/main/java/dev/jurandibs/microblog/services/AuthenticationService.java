package dev.jurandibs.microblog.services;

import dev.jurandibs.microblog.request.AuthRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthenticationService extends UserDetailsService {


    String getToken(AuthRequest auth);


    String validateJwtToken(String token);
}
