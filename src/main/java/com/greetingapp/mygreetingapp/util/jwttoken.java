package com.greetingapp.mygreetingapp.util;


import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

@Component
public class jwttoken{
    private static final String TOKEN_SECRET = "Lock";


    public String createToken(Long id)   {
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);

            String token = JWT.create()
                    .withClaim("user_id", id)
                    .sign(algorithm);
            return token;

        } catch (JWTCreationException exception) {
            exception.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return null;
    }
    public Long decodeToken(String token) {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(TOKEN_SECRET)).build();
            DecodedJWT decodedJWT = verifier.verify(token);
            return decodedJWT.getClaim("user_id").asLong();
        } catch (JWTVerificationException e) {
            throw new RuntimeException("Invalid or expired token.");
        }
    }

}