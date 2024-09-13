package com.example.Namanba.portfolio.service;

import com.example.Namanba.portfolio.dto.*;
import com.example.Namanba.portfolio.entity.Portfolio;
import com.example.Namanba.portfolio.entity.subitems.Position;
import com.example.Namanba.portfolio.repository.PortfolioRepository;
import com.example.Namanba.portfolio.repository.PositionRepository;
import com.example.Namanba.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PortfolioService {
    private final AwardService awardService;
    private final CareerService careerService;
    private final CertificationService certificationService;
    private final GPAService gpaService;
    private final LanguageCertsService languageCertsService;
    private final MajorService majorService;
    private final ResumeService resumeService;
    private final StackService stackService;

    private final PortfolioRepository portfolioRepository;
    private final PositionRepository positionRepository;

    public PortfolioResponseDto getPortfolio(User user) {
        Portfolio portfolio = portfolioRepository.findByUser(user);

        if (portfolio == null) {
            portfolio = createPortfolio(user);
        }

        List<AwardDto> awardDtos = awardService.getAwards(portfolio);
        List<CareerDto> careerDtos = careerService.getCareers(portfolio);
        List<CertificationDto> certificationDtos = certificationService.getCertifications(portfolio);
        List<GPADto> gpaDtos = gpaService.getGPAs(portfolio);
        List<LanguageCertsDto> languageCertsDtos = languageCertsService.getLanguageCerts(portfolio);
        List<MajorDto> majorDtos = majorService.getMajors(portfolio);
        List<ResumeDto> resumeDtos = resumeService.getResumes(portfolio);
        List<StackDto> stackDtos = stackService.getStacks(portfolio);

        return PortfolioResponseDto.builder()
                .awards(awardDtos)
                .careers(careerDtos)
                .certifications(certificationDtos)
                .gpas(gpaDtos)
                .languageCerts(languageCertsDtos)
                .majors(majorDtos)
                .resumes(resumeDtos)
                .stacks(stackDtos)
                .build();
    }

    public Portfolio createPortfolio(User user) {
        Portfolio portfolio = Portfolio.builder()
                .user(user)
                .position(null)
                .build();
        return portfolioRepository.save(portfolio);
    }
    

    public void updatePositions(Portfolio portfolio, String positionName) {
        Position position = positionRepository.findByPositionName(positionName);
        portfolio.updatePosition(position);
        portfolioRepository.save(portfolio);
    }
}