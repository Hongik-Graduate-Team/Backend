package com.example.Namanba.portfolio.service;

import com.example.Namanba.portfolio.adaptor.PositionAdaptor;
import com.example.Namanba.portfolio.converter.PortfolioConverter;
import com.example.Namanba.portfolio.dto.PortfolioResponseDto;
import com.example.Namanba.portfolio.entity.Portfolio;
import com.example.Namanba.portfolio.entity.subitems.Position;
import com.example.Namanba.portfolio.repository.PortfolioRepository;
import com.example.Namanba.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PortfolioService {
    private final PortfolioRepository portfolioRepository;
    private final PositionAdaptor positionAdaptor;
    private final PortfolioConverter portfolioConverter;

    public PortfolioResponseDto getPortfolio(User user) {
        Portfolio portfolio = portfolioRepository.findByUser(user);

        if (portfolio == null) {
            portfolio = createPortfolio(user);
        }
        return portfolioConverter.toPortfolioResponse(portfolio);
    }

    public Portfolio createPortfolio(User user) {
        Portfolio portfolio = Portfolio.builder()
                .user(user)
                .position(null)
                .build();
        return portfolioRepository.save(portfolio);
    }


    public void updatePositions(Portfolio portfolio, String positionName) {
        Position position = positionAdaptor.findByPositionName(positionName);
        portfolio.updatePosition(position);
        portfolioRepository.save(portfolio);
    }
}