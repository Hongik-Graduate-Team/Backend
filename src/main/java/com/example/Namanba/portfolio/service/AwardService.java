package com.example.Namanba.portfolio.service;

import com.example.Namanba.portfolio.dto.AwardDto;
import com.example.Namanba.portfolio.entity.Portfolio;
import com.example.Namanba.portfolio.entity.subitems.Award;
import com.example.Namanba.portfolio.repository.AwardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AwardService {
    private final AwardRepository awardRepository;

    public List<AwardDto> getAwards(Portfolio portfolio) {
        return awardRepository.findByPortfolio(portfolio).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private AwardDto toDto(Award award) {
        return AwardDto.builder()
                .awardId(award.getAwardId())
                .awardType(award.getAwardType())
                .awardPrize(award.getAwardPrize())
                .build();
    }

    public void createAwards(Portfolio portfolio, List<AwardDto> awardDtos) {
        List<Award> awards = awardDtos.stream()
                .map(dto -> Award.builder()
                        .awardType(dto.getAwardType())
                        .awardPrize(dto.getAwardPrize())
                        .portfolio(portfolio)
                        .build())
                .collect(Collectors.toList());

        awardRepository.saveAll(awards);
    }

    public void updateAwards(Portfolio portfolio, List<AwardDto> awardDtos) {

        List<Award> existingAwards = awardRepository.findByPortfolio(portfolio);

        awardDtos.forEach(dto -> {
            Award award = existingAwards.stream()
                    .filter(a -> a.getAwardId().equals(dto.getAwardId()))
                    .findFirst()
                    .orElseThrow(RuntimeException::new); // AwardNotFoundException("Award not found with id: " + dto.getAwardId()));

            award.updateAwardType(dto.getAwardType());
            award.updateAwardPrize(dto.getAwardPrize());
            awardRepository.save(award);
        });
    }

    public void deleteAwards(Portfolio portfolio, List<Long> awardIds) {

        List<Award> existingAwards = awardRepository.findByPortfolio(portfolio);

        List<Award> awardsToDelete = existingAwards.stream()
                .filter(a -> awardIds.contains(a.getAwardId()))
                .collect(Collectors.toList());

        if (awardsToDelete.size() != awardIds.size()) {
            throw new RuntimeException(); //AwardNotFoundException("Some awards not found");
        }

        awardRepository.deleteAll(awardsToDelete);
    }

}
