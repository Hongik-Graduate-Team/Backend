package com.example.Namanba.portfolio.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder // 수상내역
public class AwardDto {
    private Long awardId;
    private String awardType;
    private String awardPrize;
}
