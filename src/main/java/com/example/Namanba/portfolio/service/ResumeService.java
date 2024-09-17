package com.example.Namanba.portfolio.service;

import com.example.Namanba.portfolio.dto.ResumeDto;
import com.example.Namanba.portfolio.entity.Portfolio;
import com.example.Namanba.portfolio.entity.Resume;
import com.example.Namanba.portfolio.repository.ResumeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ResumeService {
    private final ResumeRepository resumeRepository;

    public List<ResumeDto> getResumes(Portfolio portfolio) {
        return resumeRepository.findByPortfolio(portfolio).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private ResumeDto toDto(Resume resume) {
        return ResumeDto.builder()
                .resumeId(resume.getResumeId())
                .questionNum(resume.getQuestionNum())
                .question(resume.getQuestion())
                .answer(resume.getAnswer())
                .build();
    }

    public void createResumes(Portfolio portfolio, List<ResumeDto> resumeDtos) {
        List<Resume> resumes = resumeDtos.stream()
                .map(dto -> Resume.builder()
                        .questionNum(dto.getQuestionNum())
                        .question(dto.getQuestion())
                        .answer(dto.getAnswer())
                        .portfolio(portfolio)
                        .build())
                .collect(Collectors.toList());

        resumeRepository.saveAll(resumes);
    }

    public void updateResumes(Portfolio portfolio, List<ResumeDto> resumeDtos) {
        resumeDtos.forEach(dto -> {
            Resume resume = resumeRepository.findById(dto.getResumeId())
                    .orElseThrow(RuntimeException::new); //ResumeNotFoundException("Resume not found with id: " + dto.getResumeId()));
            resume.updateQuestionNum(dto.getQuestionNum());
            resume.updateQuestion(dto.getQuestion());
            resume.updateAnswer(dto.getAnswer());
            resumeRepository.save(resume);
        });
    }

    public void deleteResumes(Portfolio portfolio, List<Long> resumeIds) {
        List<Resume> resumesToDelete = resumeRepository.findAllById(resumeIds);
        if (resumesToDelete.size() != resumeIds.size()) {
            throw new RuntimeException(); //ResumeNotFoundException("Some resumes not found");
        }
        resumeRepository.deleteAll(resumesToDelete);
    }
}
