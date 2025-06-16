package com.proyectofs.edutechInnovators.services;
import java.io.IOException;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Service;
import java.util.Date;
@Service
public class JwtService {
    private static final String SECRET_KEY = "clave-secreta-segura-para-firmar-el token";

    private static final long EXPIRATION_TIME = 3600000;

    private static final Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);

    public String generateToken(String username) {
        return JWT.create()
                .withSubject(username)
                .withIssuer("edutechInnovators")
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis()+EXPIRATION_TIME))
                .sign(algorithm);

    }

    public String extractUsername(String token) {
        DecodedJWT decodedJWT = JWT.require(algorithm)
                .withIssuer("edutechInnovators")
                .build()
                .verify(token);
        return decodedJWT.getSubject();
    }
}
