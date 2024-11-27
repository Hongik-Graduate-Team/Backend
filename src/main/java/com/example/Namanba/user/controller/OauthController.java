package com.example.Namanba.user.controller;

import com.example.Namanba.common.util.JwtUtil;
import com.example.Namanba.user.dto.LoginResultDto;
import com.example.Namanba.user.service.KakaoLoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Tag(name = "10. [로그인] 로그인 관련 API", description = "카카오 소셜 로그인 API입니다. ")
public class OauthController {

    private final KakaoLoginService kakaoLoginService;

    private final JwtUtil jwtUtil;

    @Value("${kakao.login-url}")
    private String kakaoLoginUri;

    // 프론트에서 카카오 로그인 페이지로 이동시키는 uri
    @GetMapping("/api/auth/kakao-login")
    public void redirectToKakaoLogin(HttpServletResponse response) throws IOException {
        response.sendRedirect(kakaoLoginUri);
    }

    // kakao로부터 인가코드를 전달받는 리다이렉트 uri
    @Operation(summary = "받은 인가코드로 액세스 토큰을 발급하는 API입니다.")
    @GetMapping("/login/oauth2/code/kakao") // Redirect URI
    public ResponseEntity<Map<String, Object>> kakaoLogin(@RequestParam("code") String authCode, HttpServletResponse response)
            throws IOException {

        LoginResultDto loginResult = kakaoLoginService.handleKakaoLogin(authCode);
        boolean isNewUser = loginResult.isNewUser();

        String token = loginResult.getToken();
        String refreshToken = loginResult.getRefreshToken();
        Long expiresIn = jwtUtil.getExpirationTime(token).getTime();

        Cookie authorization = new Cookie("Authorization", token);
        authorization.setSecure(true); // HTTPS 연결에서만 쿠키 전송
        authorization.setHttpOnly(false); // JavaScript에서 접근 가능하게 설정
        authorization.setPath("/"); // 전체 경로에 쿠키 적용
        authorization.setMaxAge(3600); // 1시간 동안 유효
        response.addCookie(authorization);

        // JSON 응답에 포함할 데이터
        Map<String, Object> tokens = new HashMap<>();
        tokens.put("token", token);
        tokens.put("refreshToken", refreshToken);
        tokens.put("expiresIn", expiresIn);

        return ResponseEntity.ok(tokens);
    }

    @Operation(summary = "리프레시 토큰을 기반으로 새로운 액세스 토큰을 발급하는 API입니다.")
    @PostMapping("/refresh-token")
    public ResponseEntity<Map<String, Object>> refreshToken(@RequestBody Map<String, String> body) {
        String refreshToken = body.get("refreshToken");
        if (refreshToken == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Refresh token is missing. Please log in again.");
        }
        Long id = jwtUtil.getUserId(refreshToken);
        String newAccessToken = jwtUtil.createToken(id);
        Long expiresIn = jwtUtil.getExpirationTime(newAccessToken).getTime();

        Map<String, Object> response = new HashMap<>();
        response.put("token", newAccessToken);
        response.put("expiresIn", expiresIn); // expiresIn을 초 단위로 변환

        return ResponseEntity.ok(response);

    }


    @GetMapping("/hello")
    public String hello() {
        return "login success!!!!!";
    }
}

