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

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws IOException, ServletException {

        // HTTP 헤더에서 Authorization 헤더 값 가져오기
        String authorizationHeader = request.getHeader("Authorization");

        // Authorization 헤더가 존재하지 않거나 Bearer로 시작하지 않는 경우 처리
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "토큰 누락");
            return;
        }

        // Bearer 토큰에서 실제 토큰 값만 분리
        String token = authorizationHeader.substring(7); // "Bearer " 이후의 토큰 값 추출

        try {
            // 토큰에서 사용자 정보를 추출하여 인증 객체 생성
            UserPrincipal userPrincipal = createPrincipalFromToken(token);

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userPrincipal, null, List.of(new SimpleGrantedAuthority("USER")));

            // 세부 정보를 설정하고 SecurityContext에 인증 정보 저장
            authenticationToken.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        } catch (ExpiredJwtException e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "토큰 만료");
            return;
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "토큰 오류");
            return;
        }

        // 요청 처리 체인 계속 실행
        filterChain.doFilter(request, response);
    }

    private UserPrincipal createPrincipalFromToken(String token) {
        User user = userRepository.findById(jwtUtil.getUserId(token))
                .orElseThrow(() -> new RuntimeException()); // 예외 처리 추후 수정

        return UserPrincipal.builder(user);
    }

    /*
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws IOException, ServletException {

        Cookie[] cookies = request.getCookies();

        String authorization = null;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("Authorization")) {
                    authorization = cookie.getValue();
                    break;
                }
            }
        }

        if (authorization == null || !authorization.startsWith("Bearer ")) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "토큰 누락");
            return;
        }

        String token = authorization.split(" ")[1];

        try {
            UserPrincipal userPrincipal = createPrincipalFromToken(token);

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userPrincipal, null, List.of(new SimpleGrantedAuthority("USER")));

            authenticationToken.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        } catch (ExpiredJwtException e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "토큰 만료");
            return;
        } catch (Exception e) {
            //logger.error(e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "토큰 오류");
            return;
        }
        filterChain.doFilter(request, response);
    }

    private UserPrincipal createPrincipalFromToken(String token) {
        User user = userRepository.findById(jwtUtil.getUserId(token))
                .orElseThrow(() -> new RuntimeException()); // 예외 처리 추후 수정

        return UserPrincipal.builder(user);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        List<String> allowedPath = Arrays.asList(
                "/hello",
                "/api/auth/kakao-login",
                "/login/oauth2/code/kakao",
                //"/api/portfolio",
                //"/api/portfolio/position",
                //"/api/career",
                //"/api/certifications",
                //"/api/majors",
                //"/api/stacks",
                //"/api/careers",
                //"/api/language-certs",
                //"/api/resumes",
                //"/api/mypage/interview",
                "/.well-known/acme-challenge/**",
                "/v3/api-docs/**",
                "/swagger-resources",
                "/swagger-resources/**",
                "/swagger-ui/**",
                "/swagger-ui.html"
        );

        String path = request.getRequestURI();

        return allowedPath.stream().anyMatch(path::equals);
    }*/

}
