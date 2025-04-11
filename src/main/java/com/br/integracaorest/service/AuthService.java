package com.br.integracaorest.service;

import com.br.integracaorest.dto.Token;
import com.br.integracaorest.exception.OAuthException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class AuthService {

    @Value("${hubspot.auth.url}")
    private String authUrl;

    @Value("${hubspot.client.id}")
    private String clientId;

    @Value("${hubspot.redirect.uri}")
    private String redirectUri;

    @Value("${hubspot.scope}")
    private String scopes;

    @Value("${hubspot.token.url}")
    private String tokenUrl;

    @Value("${hubspot.client.secret}")
    private String clientSecret;

    @Value("${hubspot.grant.type}")
    private String grantType;

    private final WebClient.Builder webClientBuilder;
    private Token currentToken;

    public AuthService(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    public ResponseEntity<String> getAuthorizationUrl() {
        try {
            String url = String.format(
                    "%s?client_id=%s&redirect_uri=%s&scope=%s&response_type=code",
                    authUrl, clientId, redirectUri, scopes);
            return ResponseEntity.ok(url);
        } catch (Exception e) {
            throw new OAuthException("Erro ao gerar URL de autorização" + e.getMessage(), e);
        }
    }

    public Mono<ResponseEntity<String>> exchangeCodeForToken(String code) {
        String requestBody = String.format(
                "grant_type=%s&client_id=%s&client_secret=%s&redirect_uri=%s&code=%s",
                grantType, clientId, clientSecret, redirectUri, code);

        return webClientBuilder.baseUrl(tokenUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .build()
                .post()
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .flatMap(response -> {
                    try {
                        currentToken = extractTokenFromResponse(response);  // Aqui o currentToken é atualizado
                        return Mono.just(ResponseEntity.ok("Token recuperado com sucesso: " + currentToken.getAccessToken()));
                    } catch (Exception e) {
                        return Mono.error(new OAuthException("Erro ao processar a resposta do HubSpot", e));
                    }
                })
                .onErrorResume(e -> {
                    return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body("Erro ao recuperar o token: " + e.getMessage()));
                });
    }

    public Mono<String> getAccessToken() {
        if (currentToken == null || currentToken.isExpired()) {
            return refreshAccessToken();
        }
        return Mono.just(currentToken.getAccessToken());
    }

    private Mono<String> refreshAccessToken() {
        if (currentToken == null || currentToken.getRefreshToken() == null) {
            return Mono.error(new OAuthException("Refresh token não disponível para renovação."));
        }

        String requestBody = String.format(
                "grant_type=refresh_token&client_id=%s&client_secret=%s&refresh_token=%s",
                clientId, clientSecret, currentToken.getRefreshToken());

        return webClientBuilder.baseUrl(tokenUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .build()
                .post()
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .flatMap(response -> {
                    try {
                        currentToken = extractTokenFromResponse(response);
                        return Mono.just(currentToken.getAccessToken());
                    } catch (JsonProcessingException e) {
                        return Mono.error(new OAuthException("Erro ao processar a resposta da renovação do token", e));
                    }
                });
    }


    private Token extractTokenFromResponse(String response) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(response);

        String accessToken = rootNode.path("access_token").asText();
        String refreshToken = rootNode.path("refresh_token").asText();
        int expiresIn = rootNode.path("expires_in").asInt();

        return new Token(accessToken, refreshToken, expiresIn);
    }
}
