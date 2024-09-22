package com.example.Namanba.portfolio.converter;

import com.example.Namanba.portfolio.dto.*;
import com.example.Namanba.portfolio.entity.Portfolio;
import com.example.Namanba.portfolio.service.*;
import com.example.Namanba.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Component
public class PortfolioConverter {

    private final AwardService awardService;
    private final CareerService careerService;
    private final CertificationService certificationService;
    private final GPAService gpaService;
    private final LanguageCertService languageCertsService;
    private final MajorService majorService;
    private final ResumeService resumeService;
    private final StackService stackService;

    public PortfolioResponseDto toPortfolioResponse(Portfolio portfolio) {
        String positionName = portfolio.getPosition().getPositionName();
        List<AwardDto> awardDtos = awardService.getAwards(portfolio);
        List<CareerDto> careerDtos = careerService.getCareers(portfolio);
        List<CertificationDto> certificationDtos = certificationService.getCertifications(portfolio);
        List<GPADto> gpaDtos = gpaService.getGPAs(portfolio);
        List<LanguageCertDto> languageCertDtos = languageCertsService.getLanguageCerts(portfolio);
        List<MajorDto> majorDtos = majorService.getMajors(portfolio);
        List<ResumeDto> resumeDtos = resumeService.getResumes(portfolio);
        List<StackDto> stackDtos = stackService.getStacks(portfolio);

        return PortfolioResponseDto.builder()
                .positionName(positionName)
                .awards(awardDtos)
                .careers(careerDtos)
                .certifications(certificationDtos)
                .gpas(gpaDtos)
                .languageCerts(languageCertDtos)
                .majors(majorDtos)
                .resumes(resumeDtos)
                .stacks(stackDtos)
                .build();
    }
}



