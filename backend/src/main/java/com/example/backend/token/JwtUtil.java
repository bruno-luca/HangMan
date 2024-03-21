package com.example.backend.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.util.Date;

public class JwtUtil {

  private static final String SECRET_KEY = "your-secret-key";
  private static final long EXPIRATION_TIME = 86400000; // 24 ore in millisecondi

  public static String generateToken(String username) {
    Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
    Date expirationDate = new Date(
      System.currentTimeMillis() + EXPIRATION_TIME
    );

    return JWT
      .create()
      .withClaim("username", username)
      .withExpiresAt(expirationDate)
      .sign(algorithm);
  }

  public static DecodedJWT verifyToken(String token) {
    Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
    return JWT.require(algorithm).build().verify(token);
  }
}
