package com.example.Namanba.user.entity;

import com.example.Namanba.common.enums.LoginType;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long userId;

    @Column(nullable = false)
    private String nickname;

    @Column(name = "profile_image_url")
    private String profileImageUrl;

    @Column(nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "login_type", nullable = false)
    private LoginType loginType;

    @Builder
    public User(String nickname, String profileImageUrl, String email, LoginType loginType) {
        //this.id = TsidCreator.getTsid().toLong();
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
        this.email = email;
        this.loginType = loginType;
    }

}

