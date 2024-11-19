package org.springboot.backend.jwt;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;

// JWT를 기반으로 사용자의 인증 정보를 나타내는 클래스
public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private final String principal; // 사용자 이름
    private final String role; // 역할 (권한)

    // 생성자: 사용자 이름과 역할을 받아 인증 객체 생성
    public JwtAuthenticationToken(String principal, String role) {
        // 역할을 권한으로 설정 (단일 권한만 설정 가능)
        super(Collections.singletonList(new SimpleGrantedAuthority(role)));
        this.principal = principal; // 사용자 이름 설정
        this.role = role; // 역할 설정
        setAuthenticated(true); // 인증된 상태로 설정
    }

    @Override
    public Object getCredentials() {
        return null; // 인증 정보 (비밀번호 등)은 사용하지 않으므로 null 반환
    }

    @Override
    public Object getPrincipal() {
        return principal; // 사용자 이름 반환
    }
}
