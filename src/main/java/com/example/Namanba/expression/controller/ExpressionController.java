package com.example.Namanba.expression.controller;

import com.example.Namanba.common.response.SuccessResponse;
import com.example.Namanba.expression.dto.request.ExpressionDataDto;
import com.example.Namanba.expression.dto.response.ExpressionEvaluationDto;
import com.example.Namanba.expression.service.ExpressionService;
import com.example.Namanba.expression.usecase.GetExpressionEvaluationUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/{interviewId}/expression")
@Tag(name = "사용자 표정 평가 API", description = "사용자의 표정을 평가하는 API 입니다.")
public class ExpressionController {
    private final ExpressionService expressionService;

    private final GetExpressionEvaluationUseCase getExpressionEvaluationUseCase;

    @Operation(summary = "면접자의 표정 데이터를 받아 평가합니다.")
    @PostMapping("")
    public SuccessResponse<Void> evaluateExpression(
            @PathVariable("interviewId") Long interviewId,
            @RequestBody ExpressionDataDto expressionDataDto){
        System.out.println("EXPRESSION 1");
        expressionService.evaluateExpression(interviewId, expressionDataDto);
        return SuccessResponse.empty();
    }

    @Operation(summary = "표정 평가 점수와 피드백 메세지를 반환합니다.")
    @GetMapping("")
    public SuccessResponse<ExpressionEvaluationDto> getExpressionData(@PathVariable("interviewId") Long interviewId){
        ExpressionEvaluationDto evaluationDto = getExpressionEvaluationUseCase.execute(interviewId);

        return SuccessResponse.of(evaluationDto);
    }
}
