package com.example.Namanba.user.controller;

import com.example.Namanba.user.dto.LoginResultDto;
import com.example.Namanba.user.service.KakaoLoginService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class OauthController {

    private final KakaoLoginService kakaoLoginService;

    @Value("${kakao.login-url}")
    private String kakaoLoginUri;

    // 프론트에서 카카오 로그인 페이지로 이동시키는 uri
    @GetMapping("/api/auth/kakao-login")
    public void redirectToKakaoLogin(HttpServletResponse response) throws IOException {
        response.sendRedirect(kakaoLoginUri);
    }

    // kakao로부터 인가코드를 전달받는 리다이렉트 uri
    @GetMapping("/login/oauth2/code/kakao") //redirect uri
    public void kakaoLogin(@RequestParam("code") String authCode, HttpServletResponse response)
            throws IOException {

        LoginResultDto loginResult = kakaoLoginService.handleKakaoLogin(authCode);
        boolean isNewUser = loginResult.isNewUser();

        String token = loginResult.getToken();

        Cookie authorization = new Cookie("Authorization", token);
        authorization.setSecure(true); // HTTPS 연결에서만 쿠키 전송 localhost에서는 허용됨. 기존 false
        authorization.setHttpOnly(false); // JavaScript에서 접근 불가=백엔드에서만 접근 가능 -> false
        authorization.setPath("/"); // 전체 경로에 대해 쿠키 적용
        authorization.setMaxAge(3600); // 1시간 동안 유효
        response.addCookie(authorization);

        response.sendRedirect("https://main--namanbatest.netlify.app?token=" + token);
    }






    @GetMapping("/hello")
    public String hello(){
        return "login success!!!!!";
    }
}

