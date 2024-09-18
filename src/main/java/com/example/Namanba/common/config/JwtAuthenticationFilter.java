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
import org.springframework.util.StringUtils;
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

        String token = resolveToken((HttpServletRequest) request);

        try {
            UserPrincipal userPrincipal = createPrincipalFromToken(token);

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userPrincipal, null, List.of(new SimpleGrantedAuthority("USER")));

            authenticationToken.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authenticationToken); // 토큰 유효성 검사하고 인증 객체 생성
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

    private String resolveToken(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")){
            return bearerToken.substring(7);
        }
        return null;
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
    }

}
