package com.br.integracaorest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Token {
    private String refreshToken;
    private Instant expirationTime;
    private String accessToken;

    public Token(String accessToken, String refreshToken, int expiresIn) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expirationTime = Instant.now().plusSeconds(expiresIn);
    }

    public boolean isExpired() {
        return Instant.now().isAfter(expirationTime);
    }
}
