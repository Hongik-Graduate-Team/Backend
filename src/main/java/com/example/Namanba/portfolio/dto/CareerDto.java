package com.example.Namanba.portfolio.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder // 경력사항
public class CareerDto {
    private Long careerId;
    private String careerType;
    private String content;
    private LocalDate startDate;
    private LocalDate endDate;
}
