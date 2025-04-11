package com.br.integracaorest.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class HubSpotConfig {
    @Value("${hubspot.client.id}")
    private String clientId;

    @Value("${hubspot.client.secret}")
    private String clientSecret;

    @Value("${hubspot.redirect.uri}")
    private String redirectUri;

    @Value("${hubspot.scope}")
    private String scope;

    @Value("${hubspot.auth.url}")
    private String authUrl;

    @Value("${hubspot.token.url}")
    private String tokenUrl;

}
