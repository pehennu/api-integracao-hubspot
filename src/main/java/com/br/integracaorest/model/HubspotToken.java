package com.br.integracaorest.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "tokens")
public class HubspotToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String token_type;

    @Column(nullable = false)
    private String access_token;

    @Column(nullable = false)
    private String refresh_token;

    @Column(nullable = false, unique = true)
    private Integer expires_in;

    public HubspotToken(String token_type, String access_token, String refresh_token, int expires_in) {
        this.token_type = token_type;
        this.access_token = access_token;
        this.refresh_token = refresh_token;
        this.expires_in = expires_in;
    }

    public HubspotToken(){
        this.token_type = "";
        this.access_token = "";
        this.refresh_token = "";
        this.expires_in = 0;
    }
}
