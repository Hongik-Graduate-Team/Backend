package com.example.Namanba.portfolio.service;

import com.example.Namanba.portfolio.dto.MajorDto;
import com.example.Namanba.portfolio.entity.Portfolio;
import com.example.Namanba.portfolio.entity.subitems.Major;
import com.example.Namanba.portfolio.repository.MajorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MajorService {
    private final MajorRepository majorRepository;

    public List<MajorDto> getMajors(Portfolio portfolio) {
        return majorRepository.findByPortfolio(portfolio).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private MajorDto toDto(Major major) {
        return MajorDto.builder()
                .majorId(major.getMajorId())
                .majorName(major.getMajorName())
                .build();
    }

    public void createMajors(Portfolio portfolio, List<MajorDto> majorDtos) {
        List<Major> majors = majorDtos.stream()
                .map(dto -> Major.builder()
                        .majorName(dto.getMajorName())
                        .portfolio(portfolio)
                        .build())
                .collect(Collectors.toList());

        majorRepository.saveAll(majors);
    }

    public void updateMajors(Portfolio portfolio, List<MajorDto> majorDtos) {
        majorDtos.forEach(dto -> {
            Major major = majorRepository.findById(dto.getMajorId())
                    .orElseThrow(RuntimeException::new);//MajorNotFoundException("Major not found with id: " + dto.getMajorId()));
            major.updateMajorName(dto.getMajorName());
            majorRepository.save(major);
        });
    }

    public void deleteMajors(Portfolio portfolio, List<Long> majorIds) {
        List<Major> majorsToDelete = majorRepository.findAllById(majorIds);
        if (majorsToDelete.size() != majorIds.size()) {
            throw new RuntimeException(); //MajorNotFoundException("Some Majors not found");
        }
        majorRepository.deleteAll(majorsToDelete);
    }
}
