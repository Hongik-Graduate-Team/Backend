package com.example.Namanba.common.util;

import com.example.Namanba.user.entity.User;
import com.example.Namanba.user.repository.UserRepository;
import io.jsonwebtoken.*;
import com.example.Namanba.common.enums.LoginType;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

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
    private final Long expirationTime5 = 1000L * 60 * 5; // 테스트용 3분

    public String createToken(Long id) {
        return Jwts.builder()
                .claim("id",String.valueOf(id))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime5))
                .signWith(SignatureAlgorithm.HS256, jwtSecretKey.getBytes())
                .compact();
    }

    public Date getExpirationTime(String token) {
        // 클레임에서 만료 시간을 가져옴
        Claims claims = getClaims(token);
        return claims.getExpiration(); // 만료 시간 반환
    }

    private final Long refreshExpirationTime = 1000L * 60 * 60 * 24 * 7; // 7 days


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
                validateAndGetAccessToken(request);
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
        // 요청에서 액세스 토큰 추출
        String accessToken = resolveToken(request);

        // 액세스 토큰이 없거나 유효하지 않으면 validateAndGetAccessToken으로 새 토큰 발급
        if (accessToken == null || !validateToken(request, accessToken)) {
            accessToken = validateAndGetAccessToken(request); // 새 액세스 토큰 발급
        }

        // 새롭게 발급된 또는 유효한 액세스 토큰에서 사용자 ID 추출
        Long userId = getUserId(accessToken);

        // 사용자 ID로 DB에서 사용자 정보 조회
        return userRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("회원이 존재하지 않습니다."));
    }



    // 리프레시 토큰 생성
    public String createRefreshToken(Long id) {
        return Jwts.builder()
                .claim("id", String.valueOf(id))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshExpirationTime))
                .signWith(SignatureAlgorithm.HS256, jwtSecretKey.getBytes())
                .compact();
    }

    // api 요청에 대해 액세스 토큰을 검증하여 유효한 액세스 토큰을 반환해주는 함수
    public String validateAndGetAccessToken(HttpServletRequest request) {
        String accessToken = resolveToken(request);

        // 1. 액세스 토큰 검증
        if (accessToken == null || !validateToken(request, accessToken)) {
            String refreshToken = request.getHeader("Refresh-Token");

            // 2. 리프레시 토큰이 없는 경우
            if (refreshToken == null) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Refresh token is missing. Please log in again.");
            }

            // 3. 리프레시 토큰 검증
            try {
                validateToken(request, refreshToken); // 리프레시 토큰 검증
            } catch (ExpiredJwtException e) {
                // 리프레시 토큰이 만료된 경우
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Refresh token expired. Please log in again.");
            } catch (Exception e) {
                // 리프레시 토큰이 유효하지 않은 경우
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid refresh token. Please log in again.");
            }

            // 4. 유효한 리프레시 토큰이 있으면 새 액세스 토큰 발급
            Long userId = getUserId(refreshToken);
            return createToken(userId);
        }

        // 5. 유효한 액세스 토큰 그대로 반환
        return accessToken;
    }


}
