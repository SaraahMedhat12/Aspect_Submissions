//package com.example.demo.security;
//
//import com.example.demo.entity.User;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.io.Decoders;
//import io.jsonwebtoken.security.Keys;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.core.Authentication;
//import org.springframework.stereotype.Component;
//
//import javax.crypto.SecretKey;
//import java.util.Date;
//
//@Component
//public class JwtUtil {
//
//    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);
//
//    @Value("${jwt.secret:eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9}")
//    private String jwtSecret;
//
//    @Value("${jwt.expiration.ms:3600000}")  // 3600000ms = 1 hour
//    private long jwtExpirationMs;
//
//
////    @Value("${jwt.expiration.ms}")
////    private int jwtExpirationMs;
//
//    private SecretKey key() {
//        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
//    }
//
//    public String generateJwtToken(Authentication authentication) {
//        User userPrincipal = (User) authentication.getPrincipal();
//        return Jwts.builder()
//                .subject(userPrincipal.getUsername())
//                .issuedAt(new Date())
//                .expiration(new Date((new Date()).getTime() + jwtExpirationMs))
//                .signWith(key(), Jwts.SIG.HS512) // Use Jwts.SIG instead of deprecated SignatureAlgorithm
//                .compact();
//    }
//
//    public String getUserNameFromJwtToken(String token) {
//        return Jwts.parser().verifyWith(key()).build().parseSignedClaims(token).getPayload().getSubject();
//    }
//
//    public boolean validateJwtToken(String authToken) {
//        try {
//            Jwts.parser().verifyWith(key()).build().parseSignedClaims(authToken);
//            return true;
//        } catch (Exception e) {
//            logger.error("Invalid JWT token: {}", e.getMessage());
//        }
//        return false;
//    }
//}
package com.example.demo.security;

import com.example.demo.entity.User_Entity;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);
    @Value("${jwt.secret:VGhpcyBpcyBhIHN0cm9uZyBzZWNyZXQga2V5IGZvcjBpbmdhdG9ycyE=}")


    private String jwtSecret;

    @Value("${jwt.expiration.ms:3600000}")
    private long jwtExpirationMs;


    private SecretKey key() {

        if (jwtSecret != null && !jwtSecret.isEmpty()) {
            byte[] decodedKey = Decoders.BASE64URL.decode(jwtSecret);

            if (decodedKey.length >= 32) {
                return Keys.hmacShaKeyFor(decodedKey);
            } else {
                throw new RuntimeException("JWT secret key must be at least 256 bits long.");
            }
        } else {

            return Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256);
        }
    }


    public String generateJwtToken(Authentication authentication) {
        User_Entity userPrincipal = (User_Entity) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(key(), io.jsonwebtoken.SignatureAlgorithm.HS256)
                .compact();
    }


    public String getUserNameFromJwtToken(String token) {
        try {

            String[] parts = token.split("\\.");
            if (parts.length != 3) {
                throw new IllegalArgumentException("Invalid JWT token.");
            }


            String payload = parts[1];
            String decodedPayload = new String(Decoders.BASE64.decode(payload));

            logger.info("Decoded JWT payload: {}", decodedPayload);


            String username = decodedPayload.split("\"sub\":\"")[1].split("\"")[0];
            return username;
        } catch (Exception e) {
            logger.error("Failed to extract username from JWT token: {}", e.getMessage());
            throw new IllegalArgumentException("Invalid JWT token.");
        }
    }


    public boolean validateJwtToken(String authToken) {
        try {
            // Split the token into header, payload, and signature
            String[] parts = authToken.split("\\.");
            if (parts.length != 3) {
                throw new IllegalArgumentException("Invalid JWT token.");
            }


            String headerPayload = parts[0] + "." + parts[1];


            String signature = parts[2];
            byte[] expectedSignature = Decoders.BASE64.decode(signature);


            byte[] actualSignature = key().getEncoded();
            if (expectedSignature != null && actualSignature != null) {

                return java.util.Arrays.equals(expectedSignature, actualSignature);
            } else {
                logger.error("Invalid JWT signature.");
            }
        } catch (Exception e) {
            logger.error("JWT validation failed: {}", e.getMessage());
        }
        return false;
    }
}
