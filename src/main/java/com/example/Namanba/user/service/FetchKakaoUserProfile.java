package com.example.Namanba.user.service;

import com.example.Namanba.user.dto.SocialUserProfileDto;

public interface FetchKakaoUserProfile {

    SocialUserProfileDto doFetch(String accessToken);
}
