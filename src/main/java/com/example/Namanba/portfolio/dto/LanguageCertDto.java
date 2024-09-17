package com.example.Namanba.portfolio.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class LanguageCertDto {
    private Long languageCertId;
    private String languageCertType;
    private String languageCertLevel;
    private LocalDate languageCertDate;
}