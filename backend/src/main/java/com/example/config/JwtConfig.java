package com.example.config;
import org.springframework.http.HttpStatus;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct; // Changed from javax to jakarta
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import org.springframework.web.server.ResponseStatusException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;


/**
 * Configuration class for generating and validating JWT tokens.
 */
@Component
public class JwtConfig {

    // Inject the secret key from application.properties (should be a strong, base64-encoded key)
    @Value("${jwt.secret}")
    private String secretKey;

    // Token validity in milliseconds (1 hour = 3600000 ms)
    private final long expiration = 1000 * 1 * 60 * 5;

    // Derived SecretKey for HMAC-SHA256 signing
    private SecretKey key;

    /**
     * Initialize the SecretKey after secretKey is injected.
     * Using @PostConstruct to ensure the key is created after dependency injection.
     */
    @PostConstruct
    public void init() {
        // Convert the secret key string to a SecretKey object for HS256
        // Ensure the secretKey is at least 32 bytes (256 bits) for HS256 security
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Generate a JWT token for a given username.
     * @param username The userId to include as the subject in the token.
     * @return The compact JWT string.
     */
    public String generateToken(String userId) {
        return Jwts.builder()
                .setSubject(userId)                       // Set the "sub" claim: userId
                .setIssuedAt(new Date())                   // Set issuance time ("iat" claim)
                .setExpiration(new Date(System.currentTimeMillis() + expiration)) // Set expiry ("exp" claim)
                // .claim("role", "whatever you want") // way to enter other information
                .signWith(key)                             // Sign with SecretKey (modern API)
                .compact();                                // Build and serialize the token
    }

    /**
     * Extract the userId (subject) from a JWT token.
     * @param token The JWT token to parse.
     * @return The username from the token's subject claim.
     */
    public String getUserId(String token) {
        return getClaims(token).getSubject();
    }

    /**
     * Check if the token has expired.
     * @param token The JWT token to check.
     * @return True if the token is expired, false otherwise.
     */
    public boolean isTokenExpired(String token) {
        return getClaims(token).getExpiration().before(new Date());
    }

    /**
     * Helper method to parse the token and retrieve its claims.
     * @param token The JWT token to parse.
     * @return The Claims object containing the token's payload.
     * @throws io.jsonwebtoken.JwtException If the token is invalid or cannot be parsed.
     */
    private Claims getClaims(String token) {
        return Jwts.parserBuilder()                    // Modern parser API
                .setSigningKey(key)                    // Set the signing key for verification
                .build()                               // Build the parser
                .parseClaimsJws(token)                 // Parse the token (throws error if invalid or expired)
                .getBody();                            // Return the claims
    }

    /**
     * Adds a new HttpOnly JWT cookie to the response.
     *
     * The cookie is named kAIn-jwt, set to HttpOnly to prevent access from JavaScript,
     * available for all endpoints (path "/"), and expires after 1 hour.
     *
     * @param token    the JWT to store inside the cookie
     * @param response the HttpServletResponse to attach the cookie to
     */
    public void addCookie(String token, HttpServletResponse response) {
        Cookie cookie = new Cookie("kAIn-jwt", token); // "jwt" is the cookie name
        cookie.setHttpOnly(true);                 // Prevents access from JS
        // cookie.setSecure(true);                   // Only sent over HTTPS
        cookie.setPath("/");                      // Available for all endpoints
        cookie.setMaxAge(3600);                   // Expiration in seconds (3600 = 1 hour); client will not send the expired cookie
        response.addCookie(cookie);
    }


    /**
     * Checks if the provided JWT is still valid and refreshes it if necessary.
     *
     * - If the token is valid, generates a new token (to extend expiration) and re-attaches it  
     *   as a cookie using {@link #addCookie(String, HttpServletResponse)}.  
     * - If the token is expired, throws {@link ResponseStatusException} with UNAUTHORIZED.  
     * - If the token is malformed or invalid, throws {@link ResponseStatusException} with FORBIDDEN.  
     * - If the token is missing (null), throws {@link ResponseStatusException} with UNAUTHORIZED.  
     *
     * @param token    the JWT string extracted from the request cookie
     * @param response the HttpServletResponse to refresh the cookie if needed
     * @throws ResponseStatusException when the token is invalid, expired, or missing
     */
    public void checkAndRefreshTokenIfNeeded(String token, HttpServletResponse response) {
        if (token == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "kAIn-cookie is not here... login pls");
        }
        try {
            boolean isValidToken = !isTokenExpired(token);
            if (isValidToken) {

                String userId = getUserId(token);
                String newToken = generateToken(userId);
                addCookie(newToken, response);
                return;
            } else {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "kAIn-jwt is expired... you must log in again");
            }
        } catch (ExpiredJwtException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "kAIn-jwt is expired... you must log in again");
        } catch (JwtException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "whatchu lookin at...");
        }
    }

    public void removeJwt(HttpServletResponse response) {
        Cookie cookie = new Cookie("kAIn-jwt", null);
        cookie.setHttpOnly(true);
        // cookie.setSecure(true); // keep if using HTTPS
        cookie.setPath("/");
        cookie.setMaxAge(0); // expire immediately
        response.addCookie(cookie);
    }
}