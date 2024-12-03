package com.example.Namanba.common.config;

import com.example.Namanba.common.UserPrincipal;
import com.example.Namanba.common.util.JwtUtil;
import com.example.Namanba.user.entity.User;
import com.example.Namanba.user.repository.UserRepository;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.nimbusds.oauth2.sdk.ciba.CIBAError.EXPIRED_TOKEN;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws IOException, ServletException {

        // 요청 URL로 재발급 요청인지 확인
        String requestURI = request.getRequestURI();
        if (requestURI.equals("/refresh-token")) {
            // 리프레시 토큰 처리
            String refreshToken = request.getHeader("Refresh-Token");
            if (refreshToken == null || refreshToken.isEmpty()) {
                System.out.println("리프레시 토큰 누락");
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "리프레시 토큰 누락");
                return;
            }

            try {
                // 리프레시 토큰에서 사용자 정보 추출
                UserPrincipal userPrincipal = createPrincipalFromToken(refreshToken);

                // 인증 객체 생성
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userPrincipal, null, List.of(new SimpleGrantedAuthority("USER")));

                authenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request));

                // SecurityContext에 인증 정보 저장
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                // 요청 처리 체인 계속 실행
                filterChain.doFilter(request, response);
                return;

            } catch (ExpiredJwtException e) {
                System.out.println("리프레시 토큰 만료: " + e.getMessage());
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "리프레시 토큰 만료");
                return;
            } catch (Exception e) {
                System.out.println("리프레시 토큰 오류");
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "리프레시 토큰 오류");
                return;
            }
        }

        // 일반 요청에 대한 처리 (Bearer 토큰)
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            System.out.println("Authorization 헤더 누락");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "토큰 누락");
            return;
        }

        String token = authorizationHeader.substring(7); // "Bearer " 이후의 토큰 값 추출

        try {
            // Bearer 토큰 처리
            UserPrincipal userPrincipal = createPrincipalFromToken(token);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userPrincipal, null, List.of(new SimpleGrantedAuthority("USER")));

            authenticationToken.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        } catch (ExpiredJwtException e) {
            System.out.println("액세스 토큰 만료: " + e.getMessage());
            //response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "토큰 만료");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        } catch (Exception e) {
            System.out.println("액세스 토큰 오류");
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "토큰 오류");
            return;
        }

        // 요청 처리 체인 계속 실행
        filterChain.doFilter(request, response);
    }


    private UserPrincipal createPrincipalFromToken(String token) {
        try {
            // 토큰에서 사용자 ID 추출
            Long userId = jwtUtil.getUserId(token);

            // 사용자 정보 조회
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            return UserPrincipal.builder(user);

        } catch (ExpiredJwtException e) {
            // 만료된 토큰의 경우 예외 처리
            System.out.println("토큰 만료됨: " + e.getMessage());
            throw new ExpiredJwtException(e.getHeader(), e.getClaims(), "토큰 만료됨");
        } catch (Exception e) {
            // 다른 예외 처리
            System.out.println("예외 발생: " + e.getMessage());
            throw new RuntimeException("Token error", e);
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        List<String> allowedPaths = Arrays.asList(
                "/api/auth/kakao-login",
                "/login/oauth2/code/kakao",
                "/hello",
                /* swagger v2 */
                "/v2/**",
                "/swagger-ui/index.html",
                "/swagger-resources",
                "/swagger-resources/**",
                "/configuration/ui",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**",
                /* swagger v3 */
                "/v3/**",
                "/v3/api-docs/swagger-config",
                "/v3/api-docs",
                "/swagger-ui/**",
                "/swagger-ui/swagger-ui-standalone-preset.js",
                "/swagger-ui/swagger-initializer.js",
                "/swagger-ui/swagger-ui-bundle.js",
                "/swagger-ui/swagger-ui.css",
                "/swagger-ui/index.css",
                "/swagger-ui/favicon-32x32.png",
                "/swagger-ui/favicon-16x16.png",
                "/api-docs/json/swagger-config",
                "/api-docs/json",
                "/h2-console/**",
                "/favicon.ico",
                "/error",
                "/"
        );

        String path = request.getRequestURI();
        return allowedPaths.stream().anyMatch(path::equals);
    }


}
