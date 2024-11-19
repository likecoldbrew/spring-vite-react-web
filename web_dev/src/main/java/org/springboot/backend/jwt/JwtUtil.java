package org.springboot.backend.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secretKey; // JWT 서명을 위한 비밀키

    @Value("${jwt.expiration}")
    private long expirationTime; // JWT 만료 시간

    // 토큰을 생성하는 메서드
    public String generateToken(String username, String role) {
        return Jwts.builder()
                .setSubject(username) // 사용자 이름 설정
                .claim("role", role) // 역할(role) 설정
                .setIssuedAt(new Date()) // 토큰 발행 시간 설정
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime)) // 토큰 만료 시간 설정
                .signWith(SignatureAlgorithm.HS256, secretKey) // 서명 알고리즘 및 비밀키 설정
                .compact(); // 최종적으로 JWT 생성
    }

    // 토큰에서 사용자 이름을 추출하는 메서드
    public String getUsernameFromToken(String token) {
        return getClaimsFromToken(token).getSubject();
    }

    // 토큰에서 역할(role)을 추출하는 메서드
    public String getRoleFromToken(String token) {
        return (String) getClaimsFromToken(token).get("role");
    }

    // 토큰이 유효한지 확인하는 메서드
    public boolean isTokenValid(String token) {
        return !isTokenExpired(token);
    }

    // 토큰에서 모든 클레임을 가져오는 메서드
    private Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey) // 비밀키 설정
                .parseClaimsJws(token) // 토큰을 파싱하여 클레임을 추출
                .getBody();
    }

    // 토큰이 만료되었는지 확인하는 메서드
    private boolean isTokenExpired(String token) {
        return getClaimsFromToken(token).getExpiration().before(new Date());
    }
}
