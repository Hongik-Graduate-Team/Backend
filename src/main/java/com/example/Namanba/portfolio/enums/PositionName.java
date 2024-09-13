package com.example.Namanba.portfolio.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PositionName {
    BACKEND_DEVELOPER("백엔드개발자"),
    FRONTEND_DEVELOPER("프론트엔드 개발자"),
    WEB_DEVELOPER("웹개발자"),
    WEB_PUBLISHER("웹 퍼블리셔"),
    APP_DEVELOPER("앱개발자"),
    GAME_DEVELOPER("게임개발자"),
    SOFTWARE_DEVELOPER("소프트웨어개발자"),
    HARDWARE_DEVELOPER("하드웨어개발자"),
    QA("QA"),
    SYSTEM_ENGINEER("시스템엔지니어"),
    NETWORK_ENGINEER("네트워크엔지니어"),
    SECURITY_ENGINEER("보안엔지니어"),
    CLOUD_ENGINEER("클라우드엔지니어"),
    IT_CONSULTING("IT 컨설팅"),
    DBA("DBA"),
    DATA_ENGINEER("데이터엔지니어"),
    DATA_SCIENTIST("데이터사이언티스트"),
    MACHINE_LEARNING_ENGINEER("머신러닝엔지니어"),
    BLOCKCHAIN_DEVELOPER("블록체인 개발자");

    private String krName;
}
