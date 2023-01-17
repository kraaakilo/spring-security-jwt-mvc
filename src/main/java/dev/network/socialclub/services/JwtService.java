package dev.network.socialclub.services;

import dev.network.socialclub.models.UserModel;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.util.*;

@Service
public class JwtService {
    private Key generateKey() {
        String JWT_S = "482B4D6251655368566D597133743677397A24432646294A404E635266556A57";
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(JWT_S));
    }

    public String extractUsername(String token) {
        return this.getClaims(token).getSubject();
    }

    public boolean isTokenValid(String token, UserDetails user) {
        try {
            return !this.getClaims(token).getExpiration().before(new Date())
                    && this.getClaims(token).getSubject().equals(user.getUsername());
        } catch (Exception e) {
            return false;
        }
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(generateKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String generateToken(UserModel userModel) {
        return this.generateToken(userModel, new HashMap<>());
    }

    public String generateToken(UserModel userModel, Map<String, Object> claims) {
        return Jwts.builder()
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plusSeconds(15000)))
                .setId(UUID.randomUUID().toString())
//                .setClaims(claims)
                .setSubject(userModel.getEmail())
                .signWith(generateKey())
                .compact();
    }
}
