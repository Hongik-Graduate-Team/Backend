package com.example.Namanba.Interview.service.processor;

import com.example.Namanba.Interview.converter.PromptConverter;
import com.example.Namanba.Interview.dto.PromptContent;
import com.example.Namanba.common.annotation.Processor;
import com.example.Namanba.common.gpt.GptProcessor;
import com.example.Namanba.portfolio.converter.PortfolioConverter;
import com.example.Namanba.portfolio.dto.PortfolioResponseDto;
import com.example.Namanba.portfolio.entity.Portfolio;
import lombok.RequiredArgsConstructor;


@Processor
@RequiredArgsConstructor
public class GenerateCustomQuestionsProcessor {
    private final PortfolioConverter portfolioConverter;
    private final PromptConverter promptConverter;
    private final GptProcessor gptProcessor;

    public String generateCustomQuestions(Portfolio portfolio) {
        PortfolioResponseDto portfolioAttributes = portfolioConverter.toPortfolioResponse(portfolio);
        PromptContent promptContent = promptConverter.toPromptContent(portfolioAttributes);
        return gptProcessor.callChatGpt(promptContent).getMessage().getContent();
    }


}
