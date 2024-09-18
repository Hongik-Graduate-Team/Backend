package com.example.Namanba.portfolio.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AwardDto {
    private Long awardId;
    private String awardType;
    private String awardPrize;
}
