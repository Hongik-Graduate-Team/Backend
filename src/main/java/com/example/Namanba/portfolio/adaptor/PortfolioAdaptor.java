package com.example.Namanba.portfolio.adaptor;

import com.example.Namanba.common.annotation.Adaptor;
import com.example.Namanba.common.exception.base.BaseException;
import com.example.Namanba.portfolio.entity.Portfolio;
import com.example.Namanba.portfolio.entity.subitems.Position;
import com.example.Namanba.portfolio.exception.PortfolioErrorCode;
import com.example.Namanba.portfolio.repository.PortfolioRepository;
import lombok.RequiredArgsConstructor;

@Adaptor
@RequiredArgsConstructor
public class PortfolioAdaptor {
    private final PortfolioRepository portfolioRepository;

    public Portfolio findByPortfolioId(Long portfolioId) {
        return portfolioRepository.findByPortfolioId(portfolioId)
                .orElseThrow(() -> new BaseException(PortfolioErrorCode.PORTFOLIO_NOT_FOUND));
    }


//    public Position findByPositionName(String positionName){
//        return positionRepository.findByPositionName(positionName)
//                .orElseThrow(() -> new BaseException(PortfolioErrorCode.POSITION_NOT_FOUND));


}
