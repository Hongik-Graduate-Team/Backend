package com.example.Namanba.gesture.controller;

import com.example.Namanba.common.response.SuccessResponse;
import com.example.Namanba.gesture.dto.request.GestureDataDto;
import com.example.Namanba.gesture.dto.response.GestureEvaluationDto;
import com.example.Namanba.gesture.usecase.EvaluateGestureUseCase;
import com.example.Namanba.gesture.usecase.GetGestureEvaluationUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/{interviewId}/evaluate-gesture")
@Tag(name = "사용자 자세 평가 API", description = "사용자의 자세를 평가하는 API 입니다.")
public class GestureController {

    private final EvaluateGestureUseCase evaluateGestureUseCase;
    private final GetGestureEvaluationUseCase getGestureEvaluationUseCase;

    @Operation(summary = "면접자의 자세 데이터를 받아온 후 평가합니다.")
    @PostMapping
    public SuccessResponse<Void> receiveGestureData(@RequestBody GestureDataDto gestureData, @PathVariable("interviewid") Long interviewId) {
        evaluateGestureUseCase.execute(interviewId, gestureData);
        return SuccessResponse.empty();
    }

    @Operation(summary = "자세 평가 결과를 반환합니다.")
    @GetMapping
    public SuccessResponse<GestureEvaluationDto> evaluateGestureData(@PathVariable("interviewid") Long interviewId) {
        GestureEvaluationDto gestureEvaluation = getGestureEvaluationUseCase.execute(interviewId);
        return SuccessResponse.of(gestureEvaluation);
    }
}