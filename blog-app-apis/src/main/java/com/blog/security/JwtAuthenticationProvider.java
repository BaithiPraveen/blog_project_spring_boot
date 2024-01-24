package com.blog.security;

import com.blog.exceptions.CustomExceptionHandler;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Component
public class JwtAuthenticationProvider {
    @Value("${app.jwt-secret}")
    private String secretKey;

    @Value("${app-jwt-expiration-milliseconds}")
    private Long jwtExpirationDate;

    /**
     * Generates a JWT token for the given authentication.
     *
     * @param authentication The authentication object containing user details.
     * @return The generated JWT token.
     */
    //Generate token
    public String generateJwtToken(Authentication authentication){
        String userName = authentication.getName();
        Date issuedAt = new Date();
        Date expireDate = new Date(issuedAt.getTime()+jwtExpirationDate);

        String token=null;
        token = Jwts
                .builder()
                .subject(userName)
                .issuedAt(issuedAt)
                .expiration(expireDate)
                .signWith(key())
                .compact();
        return token;
    }
    private Key key(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }


    // get username from Jwt token
    /**
     * Extracts the username from the given JWT token.
     *
     * @param token The JWT token from which the username needs to be extracted.
     * @return The username extracted from the JWT token.
     */
    public String getUserName(String token){
        return Jwts
                .parser()
                .verifyWith((SecretKey) key())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    /**
     * Validates the given JWT token.
     *
     * @param token The JWT token to be validated.
     * @return true if the token is valid; otherwise, false.
     * @throws CustomExceptionHandler with appropriate messages for different validation failures.
     */
    // validate Jwt token
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith((SecretKey) key())
                    .build()
                    .parse(token);
            return true;
        } catch (MalformedJwtException malformedJwtException) {
            throw new CustomExceptionHandler("Invalid Jwt Token");
        } catch (ExpiredJwtException expiredJwtException) {
            throw new CustomExceptionHandler("Toke Expired");
        } catch (UnsupportedJwtException unsupportedJwtException) {
            throw new CustomExceptionHandler("Unsupported jwt token");
        } catch (IllegalArgumentException illegalArgumentException) {
            throw new CustomExceptionHandler("Jwt claims string is null or empty");
        }

    }
}
