package com.example.Namanba.user.service;

import com.example.Namanba.common.annotation.UseCase;
import com.example.Namanba.common.exception.base.BaseException;
import com.example.Namanba.common.util.JwtUtil;
import com.example.Namanba.user.dto.UserProfileResponse;
import com.example.Namanba.user.entity.User;
import com.example.Namanba.user.exception.UserErrorCode;
import com.example.Namanba.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class GetUserProfileUseCase {
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public UserProfileResponse execute(HttpServletRequest httpRequest) {
        User user = userRepository.findByUserId(jwtUtil.getUserByToken(httpRequest).getUserId())
                .orElseThrow(() -> new BaseException(UserErrorCode.USER_NOT_FOUND));
        return user.getUserProfile();
    }
}
