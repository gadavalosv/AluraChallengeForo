package com.gadv.alura.forum.infrastructure.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.gadv.alura.forum.domain.model.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

@Service
public class TokenService {
    @Value("${api.security.secret}")
    private String apiSecret;
    public String generateToken(User user){
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            return JWT.create()
                    .withIssuer("gadv forum")
                    .withSubject(user.getUsername())
                    .withClaim("id", user.getId())
                    .withExpiresAt(generateExpirationDate())
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            throw new RuntimeException();
        }
    }

    public String getSubject(String token) {
        if (token == null) throw new RuntimeException();
        DecodedJWT verifier = null;
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            verifier = JWT.require(algorithm)
                    .withIssuer("gadv forum")
                    .build()
                    .verify(token);
            verifier.getSubject();
        } catch (JWTVerificationException exception) {
            // Invalid signature/claims
        }
        if((verifier != null ? verifier.getSubject() : null) == null){
            throw new RuntimeException("Verifier Invalido");
        }
        return verifier.getSubject();
    }

    private Instant generateExpirationDate(){
        ZoneId zoneId = ZoneId.of("Mexico/General");
        ZoneOffset zoneOffset = zoneId.getRules().getOffset(Instant.now());
        return LocalDateTime.now().plusHours(24).toInstant(zoneOffset); //tutorial: ZoneOffset.of("-06:00") instead of zoneOffset
    }
}
