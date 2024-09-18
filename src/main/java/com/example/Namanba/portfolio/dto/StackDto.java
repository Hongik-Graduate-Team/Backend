package com.example.Namanba.portfolio.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StackDto {
    private Long stackId;
    private String stackLanguage;
    private String stackLevel;
}
