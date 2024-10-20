package com.example.Namanba.evaluation.expression.controller;

import com.example.Namanba.common.response.SuccessResponse;
import com.example.Namanba.evaluation.expression.dto.ExpressionDataDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/{interviewId}/expression")
public class ExpressionController {

    @PostMapping("")
    public SuccessResponse<Void> evaluateExpression(@RequestBody ExpressionDataDto expressionDataDto){
        // 면접 중 표정 평가 서비스 로직 구현

        return new SuccessResponse<>(null);
    }
}
