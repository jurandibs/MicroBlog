package dev.jurandibs.microblog.controllers;

import dev.jurandibs.microblog.request.AuthRequest;
import dev.jurandibs.microblog.response.AuthResponse;
import dev.jurandibs.microblog.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping(path="/login")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public AuthResponse login(@RequestBody final AuthRequest auth) {

        UsernamePasswordAuthenticationToken userAuthenticationToken = new UsernamePasswordAuthenticationToken(auth.getUsername(), auth.getPassword());

        authenticationManager.authenticate(userAuthenticationToken);

        return new AuthResponse(authenticationService.getToken(auth));
    }
}
