package com.example.Namanba.user.service;

import com.example.Namanba.user.dto.SocialUserProfileDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class FetchKakaoUserProfileImpl implements FetchKakaoUserProfile{

    @Value("${spring.security.oauth2.client.provider.kakao.token-uri}")
    private String endPoint;

    public SocialUserProfileDto doFetch(String accessToken) {
        try {
            return fetchUserProfile(accessToken);
        } catch (Exception e) {
            //throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "카카오 사용자 프로필 오류");
            throw new RuntimeException(); // 예외 처리 추후 수정
        }
    }

    private SocialUserProfileDto fetchUserProfile(String accessToken)
            throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper objectMapper = new ObjectMapper();

        HttpEntity<Void> httpEntity = createProfileHttpEntity(accessToken);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                endPoint,
                HttpMethod.GET,
                httpEntity,
                String.class);

        JsonNode jsonNode = objectMapper.readTree(responseEntity.getBody());

        String nickname = parseNickname(jsonNode);
        String email = parseEmail(jsonNode);
        String profileImageUrl = parseProfileImageUrl(jsonNode);

        return new SocialUserProfileDto(nickname, email, profileImageUrl);
    }

    private HttpEntity<Void> createProfileHttpEntity(String accessToken) {
        HttpHeaders headers = new HttpHeaders();

        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        return new HttpEntity<>(headers);
    }

    private String parseNickname(JsonNode jsonNode) {
        return jsonNode.get("kakao_account")
                .get("profile")
                .get("nickname")
                .asText();
    }

    private String parseEmail(JsonNode jsonNode) {
        return jsonNode.get("kakao_account")
                .get("email")
                .asText();
    }

    private String parseProfileImageUrl(JsonNode jsonNode) {
        return jsonNode.get("kakao_account")
                .get("profile")
                .get("profile_image_url")
                .asText();
    }


}

