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

    private final Long refreshExpirationTime = 1000L * 60 * 60 * 24 * 7; // 7 days

    public String createRefreshToken(Long id) {
        return Jwts.builder()
                .claim("id", String.valueOf(id))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshExpirationTime))
                .signWith(SignatureAlgorithm.HS256, jwtSecretKey.getBytes())
                .compact();
    }

    public boolean validateToken(HttpServletRequest request, String token){
        try {
            // 토큰을 파싱하고 서명 검증 수행
            Jwts.parser()
                    .setSigningKey(jwtSecretKey.getBytes())
                    .parseClaimsJws(token);
            return true; // 유효한 토큰이면 true 반환
        }
            catch (MalformedJwtException | SignatureException | UnsupportedJwtException e) {
                request.setAttribute("exception", "토큰의 형식을 확인하세요.");
            } catch (ExpiredJwtException e) {
                request.setAttribute("exception", "access 토큰이 만료되었습니다.");
            } catch (IllegalArgumentException e) {
                request.setAttribute("exception", "JWT compact of handler are invalid");
            }
        return false; //유효하지 않다면 false 반환
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
        //String token = resolveToken(request);
        String token = validateAndGetAccessToken(request); //모든 api요청에 대해 액세스 토큰 검증
        Long userId = Long.parseLong(getClaims(token).get("id", String.class));
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("회원이 존재하지 않습니다."));
        return user;
    }

    // api 요청에 대해 액세스 토큰을 검증하여 유효한 액세스 토큰을 반환해주는 함수
    public String validateAndGetAccessToken(HttpServletRequest request){
        String accessToken = request.getHeader("Authorization");

        if (accessToken == null || !validateToken(request, accessToken)){
            String refreshToken = request.getHeader("Refresh-Token");

            // Refresh Token이 없거나 유효하지 않은 경우 예외 처리
            if (refreshToken == null || !validateToken(request, refreshToken)) {
                throw new RuntimeException("Invalid access or refresh token");
                // 리프레시 토큰을 만드는과정
            }

            // Refresh Token을 사용하여 새로운 Access Token 발급
            Long userId = getUserId(refreshToken);
            return createToken(userId);
        }
        // Access Token이 유효하면 그대로 반환
        return accessToken;
    }

}
