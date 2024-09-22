package com.example.Namanba.Interview.service.processor;

import com.example.Namanba.Interview.converter.PromptConverter;
import com.example.Namanba.Interview.dto.PromptContent;
import com.example.Namanba.common.annotation.Processor;
import com.example.Namanba.common.feign.dto.ChatResponse;
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
        // ChatResponse에서 첫 번째 선택지의 메시지를 가져옵니다.
        ChatResponse chatResponse = gptProcessor.callChatGpt(promptContent);
        ChatResponse.Choice.Message firstChoiceMessage = chatResponse.getFirstChoiceMessage();

        // 메시지가 null이 아닌지 확인하고 내용을 반환합니다.
        if (firstChoiceMessage != null) {
            return firstChoiceMessage.getContent();
        } else {
            throw new RuntimeException("First choice message is null.");
        }
    }
}