package com.example.Namanba.gaze.controller;

import com.example.Namanba.common.response.SuccessResponse;
import com.example.Namanba.gaze.dto.request.GazeDataDto;
import com.example.Namanba.gaze.dto.response.GazeEvaluationDto;
import com.example.Namanba.gaze.entity.DirectionCounts;
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
        // directionCounts 상세 데이터 출력
        DirectionCounts directionCounts = gazeData.getDirectionCounts();
        System.out.println("DirectionCounts 데이터: ");
        System.out.println("  - Up: " + directionCounts.getUp());
        System.out.println("  - Down: " + directionCounts.getDown());
        System.out.println("  - Left: " + directionCounts.getLeft());
        System.out.println("  - Right: " + directionCounts.getRight());
        System.out.println("  - CenterX: " + directionCounts.getCenterX());
        System.out.println("  - CenterY: " + directionCounts.getCenterY());

        // stabilityScore 출력
        System.out.println("Stability Score: " + gazeData.getStabilityScore());
        try {
            System.out.println("시선 데이터 처리 중...");
            evaluateGazeUseCase.execute(interviewId, gazeData);
            System.out.println("성공: 시선 데이터가 정상적으로 처리되었습니다. 인터뷰 ID: " + interviewId);
        } catch (Exception e) {
            System.out.println("오류 발생: 시선 데이터를 처리하는 중 예외가 발생했습니다. 인터뷰 ID: " + interviewId);
            e.printStackTrace(); // 예외 상세 출력
            throw e; // 예외 다시 던지기
        }

        System.out.println("시선 데이터 평가 종료 - 인터뷰 ID: " + interviewId);
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
