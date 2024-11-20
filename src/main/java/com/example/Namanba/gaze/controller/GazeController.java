package com.example.Namanba.gaze.controller;

import com.example.Namanba.common.response.SuccessResponse;
import com.example.Namanba.gaze.dto.request.GazeDataDto;
import com.example.Namanba.gaze.dto.response.GazeEvaluationDto;
import com.example.Namanba.gaze.usecase.EvaluateGazeUseCase;
import com.example.Namanba.gaze.usecase.GetGazeEvaluationUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/{interviewId}/evaluate-gaze")
@Tag(name = "사용자 시선 평가 API", description = "사용자의 시선을 평가하는 API 입니다.")
public class GazeController {

    private final EvaluateGazeUseCase evaluateGazeUseCase;
    private final GetGazeEvaluationUseCase getGazeEvaluationUseCase;

    @Operation(summary = "면접자의 시선 데이터를 받아온 후 평가합니다.")
    @PostMapping
    public SuccessResponse<Void> receiveGazeData(@RequestBody GazeDataDto gazeData, @PathVariable("interviewId") Long interviewId) {
        System.out.println("GAZE 1");
        System.out.println("시선 데이터 확인-------"+gazeData.getDirectionCounts()+"//////"+gazeData.getStabilityScore());
        evaluateGazeUseCase.execute(interviewId, gazeData);
        return SuccessResponse.empty();
    }

    @Operation(summary = "시선 평가 결과를 반환합니다.")
    @GetMapping
    public SuccessResponse<GazeEvaluationDto> evaluateGazeData(@PathVariable("interviewId") Long interviewId) {
        System.out.println("GAZE 시선 평가 결과 반환");
        GazeEvaluationDto gazeEvaluation = getGazeEvaluationUseCase.execute(interviewId);
        return SuccessResponse.of(gazeEvaluation);
    }

}
