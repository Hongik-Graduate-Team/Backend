package com.example.Namanba.user.service;

import com.example.Namanba.common.enums.LoginType;
import com.example.Namanba.common.util.JwtUtil;
import com.example.Namanba.user.dto.LoginResultDto;
import com.example.Namanba.user.dto.SocialUserProfileDto;
import com.example.Namanba.user.entity.User;
import com.example.Namanba.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class KakaoLoginService {

    private final UserRepository userRepository;

    private final ExchangeKakaoAccessToken exchangeKakaoAccessToken;

    private final FetchKakaoUserProfile fetchKakaoUserProfile;

    private final JwtUtil jwtUtil;

    private static final LoginType KAKAO_LOGIN_TYPE = LoginType.KAKAO;

    @Transactional
    public LoginResultDto handleKakaoLogin(String authorizationCode) {
        String accessToken = exchangeKakaoAccessToken(authorizationCode);
        SocialUserProfileDto userProfile = fetchKakaoUserProfile(accessToken);

        Optional<User> foundUser = userRepository.findByEmailAndLoginType(userProfile.getEmail(), KAKAO_LOGIN_TYPE);
        boolean isNewUser = foundUser.isEmpty();

        User user = foundUser.orElseGet(() -> registerUser(userProfile));

        String token = createToken(user);

        return new LoginResultDto(token, isNewUser);
    }

    private String exchangeKakaoAccessToken(String authorizationCode) {
        return exchangeKakaoAccessToken.doExchange(authorizationCode);
    }

    private SocialUserProfileDto fetchKakaoUserProfile(String accessToken) {
        return fetchKakaoUserProfile.doFetch(accessToken);
    }

    private String createToken(User user) {
        return jwtUtil.createToken(user.getUserId());
    }

    private User registerUser(SocialUserProfileDto userProfile) {
        User user = new User(
                userProfile.getNickname(),
                userProfile.getProfileImageUrl(),
                userProfile.getEmail(),
                KAKAO_LOGIN_TYPE);

        return userRepository.save(user);
    }
}
