package com.example.Namanba.portfolio.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class PortfolioResponseDto {
    private List<AwardDto> awards;
    private List<CareerDto> careers;
    private List<CertificationDto> certifications;
    private List<GPADto> gpas;
    private List<LanguageCertDto> languageCerts;
    private List<MajorDto> majors;
    private List<ResumeDto> resumes;
    private List<StackDto> stacks;
}