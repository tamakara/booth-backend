package com.tamakara.booth.backend.common.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.time.Instant;
import java.util.UUID;

public class JwtUtil {
    private static final String secret = "tamakara";

    public static String generateJWT(long userId, long expiration) {
        return JWT
                .create()
                .withExpiresAt(Instant.now().plusSeconds(expiration))
                .withSubject(String.valueOf(userId))
                .withClaim("jti", UUID.randomUUID().toString())
                .sign(Algorithm.HMAC256(secret));
    }

    public static long decodeJWT(String jwt) {
        return Long.parseLong(JWT
                .require(Algorithm.HMAC256(secret))
                .build()
                .verify(jwt)
                .getSubject());
    }
}
