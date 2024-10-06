package com.example.Namanba.portfolio.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class PortfolioResponseDto {
    private String positionName;
    private List<MajorDto> majors; //학적
    private List<GPADto> gpas; //학점
    private List<CareerDto> careers; //경력사항
    private List<StackDto> stacks; //기술스택
    private List<AwardDto> awards; //수상내역
    private List<CertificationDto> certifications; //자격증
    private List<LanguageCertDto> languageCerts; //어학자격증
    private List<ResumeDto> resumes; //자기소개서
}