package com.example.Namanba.portfolio.service;

import com.example.Namanba.portfolio.dto.CareerDto;
import com.example.Namanba.portfolio.entity.Portfolio;
import com.example.Namanba.portfolio.entity.subitems.Career;
import com.example.Namanba.portfolio.repository.CareerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CareerService {
    private final CareerRepository careerRepository;
    public List<CareerDto> getCareers(Portfolio portfolio) {
        return careerRepository.findByPortfolio(portfolio).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private CareerDto toDto(Career career) {
        return CareerDto.builder()
                .careerId(career.getCareerId())
                .careerType(career.getCareerType())
                .content(career.getContent())
                .startDate(career.getStartDate())
                .endDate(career.getEndDate())
                .build();
    }
    public void createCareers(Portfolio portfolio, List<CareerDto> careerDtos) {
        List<Career> careers = careerDtos.stream()
                .map(dto -> Career.builder()
                        .careerType(dto.getCareerType())
                        .content(dto.getContent())
                        .startDate(dto.getStartDate())
                        .endDate(dto.getEndDate())
                        .portfolio(portfolio)
                        .build())
                .collect(Collectors.toList());

        careerRepository.saveAll(careers);
    }

    public void updateCareers(Portfolio portfolio, List<CareerDto> careerDtos) {
        careerDtos.forEach(dto -> {
            Career career = careerRepository.findById(dto.getCareerId())
                    .orElseThrow(RuntimeException::new); //new CareerNotFoundException("Career not found with id: " + dto.getCareerId()));
            career.updateCareerType(dto.getCareerType());
            career.updateContent(dto.getContent());
            career.updateStartDate(dto.getStartDate());
            career.updateEndDate(dto.getEndDate());
            careerRepository.save(career);
        });
    }

    public void deleteCareers(Portfolio portfolio, List<Long> careerIds) {
        List<Career> careersToDelete = careerRepository.findAllById(careerIds);
        if (careersToDelete.size() != careerIds.size()) {
            throw new RuntimeException(); //CareerNotFoundException("Some careers not found");
        }
        careerRepository.deleteAll(careersToDelete);
    }
}
