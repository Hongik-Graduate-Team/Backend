package com.example.Namanba.portfolio.service;

import com.example.Namanba.portfolio.dto.LanguageCertsDto;
import com.example.Namanba.portfolio.entity.Portfolio;
import com.example.Namanba.portfolio.entity.subitems.LanguageCerts;
import com.example.Namanba.portfolio.repository.LanguageCertsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class LanguageCertsService {
    private final LanguageCertsRepository languageCertsRepository;

    public List<LanguageCertsDto> getLanguageCerts(Portfolio portfolio) {
        return languageCertsRepository.findByPortfolio(portfolio).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private LanguageCertsDto toDto(LanguageCerts languageCerts) {
        return LanguageCertsDto.builder()
                .languageCertsId(languageCerts.getLanguageCertsId())
                .languageCertsType(languageCerts.getLanguageCertsType())
                .languageCertsLevel(languageCerts.getLanguageCertsLevel())
                .languageCertsDate(languageCerts.getLanguageCertsDate())
                .build();
    }

    public void createLanguageCerts(Portfolio portfolio, List<LanguageCertsDto> languageCertsDtos) {
        List<LanguageCerts> languageCerts = languageCertsDtos.stream()
                .map(dto -> LanguageCerts.builder()
                        .languageCertsType(dto.getLanguageCertsType())
                        .languageCertsLevel(dto.getLanguageCertsLevel())
                        .languageCertsDate(dto.getLanguageCertsDate())
                        .portfolio(portfolio)
                        .build())
                .collect(Collectors.toList());

        languageCertsRepository.saveAll(languageCerts);
    }

    public void updateLanguageCerts(Portfolio portfolio, List<LanguageCertsDto> languageCertsDtos) {
        languageCertsDtos.forEach(dto -> {
            LanguageCerts languageCerts = languageCertsRepository.findById(dto.getLanguageCertsId())
                    .orElseThrow(RuntimeException::new); //LanguageCertsNotFoundException("LanguageCerts not found with id: " + dto.getLanguageCertsId()));
            languageCerts.updateLanguageCertsType(dto.getLanguageCertsType());
            languageCerts.updateLanguageCertsLevel(dto.getLanguageCertsLevel());
            languageCerts.updateLanguageCertsDate(dto.getLanguageCertsDate());
            languageCertsRepository.save(languageCerts);
        });
    }

    public void deleteLanguageCerts(Portfolio portfolio, List<Long> languageCertsIds) {
        List<LanguageCerts> languageCertsToDelete = languageCertsRepository.findAllById(languageCertsIds);
        if (languageCertsToDelete.size() != languageCertsIds.size()) {
            throw new RuntimeException(); //LanguageCertsNotFoundException("Some LanguageCerts not found");
        }
        languageCertsRepository.deleteAll(languageCertsToDelete);
    }
}
