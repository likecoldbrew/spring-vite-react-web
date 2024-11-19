package org.springboot.backend.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springboot.backend.jwt.JwtAuthenticationToken;
import org.springboot.backend.jwt.JwtUtil;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// JWT 토큰을 검증하고, 유효할 경우 사용자 인증 정보를 설정하는 필터
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil; // JWT 유틸리티 클래스

    // 생성자에서 JwtUtil 객체를 주입받음
    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        // "Authorization" 헤더에서 JWT 토큰 가져오기
        String header = request.getHeader("Authorization");

        // 헤더가 "Bearer "로 시작할 경우 토큰 파싱
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7); // "Bearer " 이후의 토큰 문자열 추출
            if (jwtUtil.isTokenValid(token)) { // 토큰이 유효한 경우
                String username = jwtUtil.getUsernameFromToken(token); // 사용자 이름 가져오기
                String role = jwtUtil.getRoleFromToken(token); // 역할(role) 가져오기

                // 사용자 인증 객체 생성 및 설정
                JwtAuthenticationToken authentication = new JwtAuthenticationToken(username, role);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication); // 인증 설정
            }
        }

        // 다음 필터로 요청을 전달
        chain.doFilter(request, response);
    }
}
