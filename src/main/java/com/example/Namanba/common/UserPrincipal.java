package com.example.Namanba.common;

import com.example.Namanba.common.enums.LoginType;
import com.example.Namanba.user.entity.User;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class UserPrincipal implements Serializable { //기존 accountId 제거

    private static final long serialVersionUID = 1L;

    private final Long id;
    private final String nickname;
    private final String profileImageUrl;
    private final String email;
    private final LoginType loginType;

    public UserPrincipal(Long id, String nickname, String profileImageUrl,
                         String email, LoginType loginType) {
        this.id = id;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
        this.email = email;
        this.loginType = loginType;
    }


    public static UserPrincipal builder(User user) {
        return new UserPrincipal(
                user.getUserId(),
                user.getNickname(),
                user.getProfileImageUrl(),
                user.getEmail(),
                user.getLoginType());
    }
}