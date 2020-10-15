package com.lbt.icon.demanddraft.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Primary
@Getter
@Configuration
@NoArgsConstructor
public class DDProductServiceConfig {
    @Value("${auth.jwt.signing-key}")
    private String jwtSigningKey;
    @Value("${auth.password-special-chars}")
    private String passwordSpecialChars;

    @Value("${auth.client.id}")
    private String authClientId;
    @Value("${auth.client.secret}")
    private String authClientSecret;
    @Value("${auth.server.link}")
    private String authServerLink;


    @Value("${auth.grant-type}")
    private String authGrantType;
    @Value("${auth.scopes}")
    private String authScopes;

}
