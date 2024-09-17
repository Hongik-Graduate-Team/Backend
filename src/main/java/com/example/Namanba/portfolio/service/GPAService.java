package com.example.Namanba.portfolio.service;

import com.example.Namanba.portfolio.dto.GPADto;
import com.example.Namanba.portfolio.entity.Portfolio;
import com.example.Namanba.portfolio.entity.subitems.GPA;
import com.example.Namanba.portfolio.repository.GPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class GPAService {
    private final GPARepository gpaRepository;

    public List<GPADto> getGPAs(Portfolio portfolio) {
        return gpaRepository.findByPortfolio(portfolio).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private GPADto toDto(GPA gpa) {
        return GPADto.builder()
                .gpaId(gpa.getGpaId())
                .score(gpa.getScore())
                .total(gpa.getTotal())
                .build();
    }

    public void createGPAs(Portfolio portfolio, List<GPADto> gpaDtos) {
        List<GPA> gpas = gpaDtos.stream()
                .map(dto -> GPA.builder()
                        .score(dto.getScore())
                        .total(dto.getTotal())
                        .portfolio(portfolio)
                        .build())
                .collect(Collectors.toList());

        gpaRepository.saveAll(gpas);
    }

    public void updateGPAs(Portfolio portfolio, List<GPADto> gpaDtos) {
        gpaDtos.forEach(dto -> {
            GPA gpa = gpaRepository.findById(dto.getGpaId())
                    .orElseThrow(RuntimeException::new); //GPANotFoundException("GPA not found with id: " + dto.getGpaId()));
            gpa.updateScore(dto.getScore());
            gpa.updateTotal(dto.getTotal());
            gpaRepository.save(gpa);
        });
    }

    public void deleteGPAs(Portfolio portfolio, List<Long> gpaIds) {
        List<GPA> gpasToDelete = gpaRepository.findAllById(gpaIds);
        if (gpasToDelete.size() != gpaIds.size()) {
            throw new RuntimeException(); //GPANotFoundException("Some GPAs not found");
        }
        gpaRepository.deleteAll(gpasToDelete);
    }
}
