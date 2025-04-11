package com.br.integracaorest.controller;

import com.br.integracaorest.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService service;

    @GetMapping("/authorize-url")
    public ResponseEntity<String> getAuthUrl() {
        return service.getAuthorizationUrl();
    }

    @GetMapping("/callback")
    public Mono<ResponseEntity<String>> oauthCallback(@RequestParam String code) {
        return service.exchangeCodeForToken(code);
    }

}
