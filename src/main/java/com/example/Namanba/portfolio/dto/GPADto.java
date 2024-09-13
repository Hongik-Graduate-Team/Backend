package com.example.Namanba.portfolio.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GPADto {
    private Long gpaId;
    private String score;
    private String total;
}
