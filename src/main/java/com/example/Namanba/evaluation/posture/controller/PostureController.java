package com.example.Namanba.evaluation.posture.controller;

import com.example.Namanba.common.response.SuccessResponse;
import com.example.Namanba.evaluation.posture.dto.request.PostureDataDto;
import com.example.Namanba.evaluation.posture.dto.response.PostureResultDto;
import com.example.Namanba.evaluation.posture.service.PostureService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/{interviewid}/evaluate-posture")
@Tag(name = "사용자 자세 평가 API", description = "사용자의 자세를 평가하는 API 입니다.")
public class PostureController {

    private final PostureService postureService;

    @Operation(summary = "프론트로부터 면접자의 자세 데이터를 받아옵니다.")
    @PostMapping
    public SuccessResponse<Void> receivePostureData(@RequestBody PostureDataDto postureData, @PathVariable("interviewid") Long interviewId) {
        postureService.execute(interviewId, postureData);
    }

    @Operation(summary = "자세 평가 결과를 반환합니다.")
    public SuccessResponse<PostureResultDto> evaluatePostureData() {

        return SuccessResponse.of(postureResult);
    }

}