package com.example.Namanba.expression.controller;

import com.example.Namanba.common.response.SuccessResponse;
import com.example.Namanba.expression.dto.request.ExpressionDataDto;
import com.example.Namanba.expression.dto.response.ExpressionEvaluationDto;
import com.example.Namanba.expression.service.ExpressionService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/{interviewId}/expression")
public class ExpressionController {
    private final ExpressionService expressionService;

    @Operation(summary = "면접자의 표정 데이터를 받아 평가합니다.")
    @PostMapping("")
    public SuccessResponse<Void> evaluateExpression(
            @PathVariable("interviewid") Long interviewId,
            @RequestBody ExpressionDataDto expressionDataDto){
        expressionService.evaluateExpression(interviewId, expressionDataDto);
        return SuccessResponse.empty();
    }

    @Operation(summary = "표정 평가 점수와 피드백 메세지를 반환합니다.")
    @GetMapping("")
    public SuccessResponse<ExpressionEvaluationDto> getExpressionData(@PathVariable("interviewid") Long interviewId){
        ExpressionEvaluationDto evaluationDto = expressionService.getExpressionEvaluationData(interviewId);
        return SuccessResponse.of(evaluationDto);
    }
}
