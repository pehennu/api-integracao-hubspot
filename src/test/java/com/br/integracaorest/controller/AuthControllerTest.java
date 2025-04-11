package com.br.integracaorest.controller;

import com.br.integracaorest.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AuthControllerTest {

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAuthUrl() {
        when(authService.getAuthorizationUrl()).thenReturn(ResponseEntity.ok("http://auth.url"));

        ResponseEntity<String> response = authController.getAuthUrl();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("http://auth.url", response.getBody());
    }

    @Test
    void testOauthCallback() {
        String code = "test-code";
        when(authService.exchangeCodeForToken(code))
                .thenReturn(Mono.just(ResponseEntity.ok("token-recebido")));

        Mono<ResponseEntity<String>> responseMono = authController.oauthCallback(code);
        ResponseEntity<String> response = responseMono.block();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("token-recebido", response.getBody());
    }
}

