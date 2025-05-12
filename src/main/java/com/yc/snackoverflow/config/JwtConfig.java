package com.yc.snackoverflow.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.Key;
import java.util.Base64;
import javax.crypto.spec.SecretKeySpec;

/**
 * JWT configuration
 */
@Configuration
public class JwtConfig {

    @Value("${app.jwt.secret:XjPbdHPiZDO71cj1QAjyaBTIQqZL5U97Gg+GEbUW4zM=}")
    private String jwtSecret;

    @Value("${app.jwt.expiration:86400000}") // Default: 24 hours
    private long jwtExpiration;

    @Value("${app.jwt.refresh-expiration:604800000}") // Default: 7 days
    private long refreshExpiration;

    /**
     * Get the JWT signing key
     * @return The signing key
     */
    @Bean
    public Key signingKey() {
        byte[] keyBytes = Base64.getDecoder().decode(jwtSecret);
        return new SecretKeySpec(keyBytes, "HmacSHA256");
    }

    /**
     * Get the JWT expiration time in milliseconds
     * @return JWT expiration time
     */
    @Bean
    public long getJwtExpiration() {
        return jwtExpiration;
    }

    /**
     * Get the JWT refresh token expiration time in milliseconds
     * @return JWT refresh expiration time
     */
    @Bean
    public long getRefreshExpiration() {
        return refreshExpiration;
    }
}
