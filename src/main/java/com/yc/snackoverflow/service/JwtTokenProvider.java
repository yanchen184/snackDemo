package com.yc.snackoverflow.service;

import com.yc.snackoverflow.constant.ApiPath;
import com.yc.snackoverflow.exception.WebErrorEnum;
import com.yc.snackoverflow.exception.WebException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Service for JWT token generation and validation
 */
@Slf4j
@Service
public class JwtTokenProvider {

    private final Key signingKey;
    private final long jwtExpiration;
    private final long refreshExpiration;
    private final HttpServletRequest request;

    @Autowired
    public JwtTokenProvider(Key signingKey, 
                           long getJwtExpiration, 
                           long getRefreshExpiration, 
                           HttpServletRequest request) {
        this.signingKey = signingKey;
        this.jwtExpiration = getJwtExpiration;
        this.refreshExpiration = getRefreshExpiration;
        this.request = request;
    }

    /**
     * Extract username from JWT token
     * 
     * @param token JWT token
     * @return Username
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extract expiration date from JWT token
     * 
     * @param token JWT token
     * @return Expiration date
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extract a specific claim from JWT token
     * 
     * @param token JWT token
     * @param claimsResolver Function to resolve the claim
     * @return Claim value
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Extract all claims from JWT token
     * 
     * @param token JWT token
     * @return All claims
     */
    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(signingKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            // For refresh token endpoint, we allow expired tokens
            if (isRefreshTokenEndpoint()) {
                return e.getClaims();
            }
            throw e;
        }
    }

    /**
     * Check if token is expired
     * 
     * @param token JWT token
     * @return true if expired
     */
    public boolean isTokenExpired(String token) {
        try {
            final Date expiration = extractExpiration(token);
            return expiration.before(new Date());
        } catch (ExpiredJwtException e) {
            return true;
        }
    }

    /**
     * Generate a token for a user
     * 
     * @param userDetails User details
     * @return JWT token
     */
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    /**
     * Generate a token with extra claims
     * 
     * @param extraClaims Extra claims
     * @param userDetails User details
     * @return JWT token
     */
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return buildToken(extraClaims, userDetails, jwtExpiration);
    }

    /**
     * Generate a refresh token
     * 
     * @param userDetails User details
     * @return Refresh token
     */
    public String generateRefreshToken(UserDetails userDetails) {
        return buildToken(new HashMap<>(), userDetails, refreshExpiration);
    }

    /**
     * Build a token
     * 
     * @param extraClaims Extra claims
     * @param userDetails User details
     * @param expiration Expiration time
     * @return Token
     */
    private String buildToken(Map<String, Object> extraClaims, UserDetails userDetails, long expiration) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Validate a token for a user
     * 
     * @param token JWT token
     * @param userDetails User details
     * @return true if valid
     */
    public boolean validateToken(String token, UserDetails userDetails) {
        try {
            final String username = extractUsername(token);
            return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
        } catch (SignatureException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Check if current request is for refresh token endpoint
     * 
     * @return true if refresh endpoint
     */
    private boolean isRefreshTokenEndpoint() {
        String path = request.getRequestURI();
        return ApiPath.RENEW_TOKEN.equals(path);
    }
}
