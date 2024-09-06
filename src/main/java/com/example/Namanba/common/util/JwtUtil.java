package com.example.Namanba.common.util;

import com.example.Namanba.user.entity.User;
import com.example.Namanba.user.repository.UserRepository;
import io.jsonwebtoken.*;
import com.example.Namanba.common.enums.LoginType;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.*;

@RequiredArgsConstructor
@Component
public class JwtUtil {

    private final UserRepository userRepository;

    @Value("${jwt.secretKey}")
    private String jwtSecretKey;

    //@Value("${JWT_EXPIRATION_TIME}")
    private final Long expirationTime = 1000L * 60 * 120; // 2h

    public String createToken(Long id) {
        return Jwts.builder()
                .claim("id",String.valueOf(id))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(SignatureAlgorithm.HS256, jwtSecretKey.getBytes())
                .compact();
    }

    public Long getUserId(String token) {
        return Long.parseLong(getClaims(token).get("id", String.class));
    }

    public LoginType getLoginType(String token) {
        return LoginType.valueOf(getClaims(token).get("loginType", String.class));
    }

    private String resolveToken(HttpServletRequest request) {
        String token = null;
        // 헤더에서 가져오는 경우
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            token = header.substring(7); // "Bearer " 이후의 값 추출
        }

        // 쿠키에서 가져오는 경우 (예시)
        if (token == null && request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("Authorization".equals(cookie.getName())) {
                    token = cookie.getValue();
                    break;
                }
            }
        }

        return token;
    }


    private Claims getClaims(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecretKey.getBytes())
                .parseClaimsJws(token).getBody();
        return claims;
    }

    public User getUserByToken(HttpServletRequest request) {
        String token = resolveToken(request);
        Long userId = Long.parseLong(getClaims(token).get("id", String.class));
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("회원이 존재하지 않습니다."));
        return user;
    }

}
