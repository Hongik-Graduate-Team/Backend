package com.example.Namanba.portfolio.service;

import com.example.Namanba.portfolio.dto.LanguageCertDto;
import com.example.Namanba.portfolio.entity.Portfolio;
import com.example.Namanba.portfolio.entity.subitems.LanguageCert;
import com.example.Namanba.portfolio.repository.LanguageCertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class LanguageCertService {
    private final LanguageCertRepository languageCertRepository;

    public List<LanguageCertDto> getLanguageCerts(Portfolio portfolio) {
        return languageCertRepository.findByPortfolio(portfolio).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private LanguageCertDto toDto(LanguageCert languageCert) {
        return LanguageCertDto.builder()
                .languageCertId(languageCert.getLanguageCertId())
                .languageCertType(languageCert.getLanguageCertType())
                .languageCertLevel(languageCert.getLanguageCertLevel())
                .languageCertDate(languageCert.getLanguageCertDate())
                .build();
    }

    public void createLanguageCert(Portfolio portfolio, List<LanguageCertDto> languageCertDtos) {
        List<LanguageCert> languageCerts = languageCertDtos.stream()
                .map(dto -> LanguageCert.builder()
                        .languageCertType(dto.getLanguageCertType())
                        .languageCertLevel(dto.getLanguageCertLevel())
                        .languageCertDate(dto.getLanguageCertDate())
                        .portfolio(portfolio)
                        .build())
                .collect(Collectors.toList());

        languageCertRepository.saveAll(languageCerts);
    }

    public void updateLanguageCert(Portfolio portfolio, List<LanguageCertDto> languageCertDtos) {
        languageCertDtos.forEach(dto -> {
            LanguageCert languageCert = languageCertRepository.findById(dto.getLanguageCertId())
                    .orElseThrow(RuntimeException::new); //LanguageCertsNotFoundException("LanguageCerts not found with id: " + dto.getLanguageCertsId()));
            languageCert.updateLanguageCertsType(dto.getLanguageCertType());
            languageCert.updateLanguageCertsLevel(dto.getLanguageCertLevel());
            languageCert.updateLanguageCertsDate(dto.getLanguageCertDate());
            languageCertRepository.save(languageCert);
        });
    }

    public void deleteLanguageCert(Portfolio portfolio, List<Long> languageCertsIds) {
        List<LanguageCert> languageCertToDelete = languageCertRepository.findAllById(languageCertsIds);
        if (languageCertToDelete.size() != languageCertsIds.size()) {
            throw new RuntimeException(); //LanguageCertsNotFoundException("Some LanguageCerts not found");
        }
        languageCertRepository.deleteAll(languageCertToDelete);
    }
}