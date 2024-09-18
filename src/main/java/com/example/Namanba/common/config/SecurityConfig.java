package com.example.Namanba.common.config;

import com.example.Namanba.common.util.JwtUtil;
import com.example.Namanba.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtUtil jwtUtil;

    private final UserRepository userRepository;

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOriginPatterns(Arrays.asList("http://localhost:3000", "https://deploy-preview-15--namanbatest.netlify.app", "https://namanba.shop",
                "https://main--namanbatest.netlify.app/"));
        config.addAllowedMethod("*");
        config.addAllowedHeader("*");
        config.setAllowCredentials(true);
        config.addExposedHeader("Authorization");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // jwt 사용하기 때문에 세션 비사용
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(swaggerUrlPatterns).permitAll()
                        .requestMatchers("/hello",
                                "/api/auth/kakao-login",
                                "/login/oauth2/code/kakao",
                                "/api/portfolio",
                                "/api/portfolio/position",
                                "/api/career",
                                "/hello",
                                "/api/certifications",
                                "/api/majors",
                                "/api/stacks",
                                "/api/careers",
                                "/api/language-certs",
                                "/api/resumes",
                                "/api/mypage/interview"
                                ).permitAll()
                        .anyRequest().authenticated() // 나머지 경로는 인증 필요
                )
                .addFilterBefore(new JwtAuthenticationFilter(jwtUtil, userRepository), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    private final String[] swaggerUrlPatterns = {
            "/v3/api-docs/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
    };

}
