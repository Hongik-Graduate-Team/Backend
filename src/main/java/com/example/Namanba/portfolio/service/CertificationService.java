package com.example.Namanba.portfolio.service;

import com.example.Namanba.portfolio.dto.CertificationDto;
import com.example.Namanba.portfolio.entity.Portfolio;
import com.example.Namanba.portfolio.entity.subitems.Certification;
import com.example.Namanba.portfolio.repository.CertificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CertificationService {
    private final CertificationRepository certificationRepository;

    public List<CertificationDto> getCertifications(Portfolio portfolio) {
        return certificationRepository.findByPortfolio(portfolio).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private CertificationDto toDto(Certification certification) {
        return CertificationDto.builder()
                .certId(certification.getCertId())
                .certType(certification.getCertType())
                .certDate(certification.getCertDate())
                .build();
    }

    public void createCertifications(Portfolio portfolio, List<CertificationDto> certificationDtos) {
        List<Certification> certifications = certificationDtos.stream()
                .map(dto -> Certification.builder()
                        .certType(dto.getCertType())
                        .certDate(dto.getCertDate())
                        .portfolio(portfolio)
                        .build())
                .collect(Collectors.toList());

        certificationRepository.saveAll(certifications);
    }

    public void updateCertifications(Portfolio portfolio, List<CertificationDto> certificationDtos) {
        certificationDtos.forEach(dto -> {
            Certification certification = certificationRepository.findById(dto.getCertId())
                    .orElseThrow(RuntimeException::new);//CertificationNotFoundException("Certification not found with id: " + dto.getCertId()));
            certification.updateCertType(dto.getCertType());
            certification.updateCertDate(dto.getCertDate());
            certificationRepository.save(certification);
        });
    }

    public void deleteCertifications(Portfolio portfolio, List<Long> certificationIds) {
        List<Certification> certificationsToDelete = certificationRepository.findAllById(certificationIds);
        if (certificationsToDelete.size() != certificationIds.size()) {
            throw new RuntimeException(); //CertificationNotFoundException("Some certifications not found");
        }
        certificationRepository.deleteAll(certificationsToDelete);
    }
}

