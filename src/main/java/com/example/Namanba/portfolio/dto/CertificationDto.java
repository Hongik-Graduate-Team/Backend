package com.example.Namanba.portfolio.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class CertificationDto {
    private Long certId;
    private String certType;
    private LocalDate certDate;
}
