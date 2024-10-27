package com.example.Namanba.expression.usecase;

import com.example.Namanba.expression.dto.request.ExpressionDataDto;
import com.example.Namanba.expression.service.ExpressionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EvaluateExpressionUseCase {
    private final ExpressionService expressionService;

    public void execute(Long interviewId, ExpressionDataDto expressionDataDto){

    }
}
