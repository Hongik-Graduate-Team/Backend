package com.example.Namanba.portfolio.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class LanguageCertsDto {
    private Long languageCertsId;
    private String languageCertsType;
    private String languageCertsLevel;
    private LocalDate languageCertsDate;
}
