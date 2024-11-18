package com.example.Namanba.Interview.controller;

import com.example.Namanba.Interview.dto.request.InterviewPageRequest;
import com.example.Namanba.Interview.dto.response.InterviewListResponse;
import com.example.Namanba.Interview.dto.response.InterviewReportResponse;
import com.example.Namanba.Interview.usecase.ReadInterviewListUseCase;
import com.example.Namanba.Interview.usecase.ReadInterviewReportUseCase;
import com.example.Namanba.common.response.SuccessResponse;
import com.example.Namanba.common.util.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/interviews")
@Tag(name = "인터뷰 및 평가 조회 API", description = "인터뷰 목록과 상세 정보를 조회합니다.")
public class InterviewReportController {

    private final ReadInterviewReportUseCase readInterviewReportUseCase;
    private final ReadInterviewListUseCase readInterviewListUseCase;

    @Operation(summary = "사용자 인터뷰 레포트 목록 조회",
            description = "파라미터가 없는 경우, 기본값으로 page=1, 정렬 기준은 createdDate의 내림차순입니다.")
    @GetMapping("/lists")
    public SuccessResponse<Page<InterviewListResponse>> getInterviewList(@ModelAttribute InterviewPageRequest interviewPageRequest, HttpServletRequest httpRequest) {
        return SuccessResponse.of(readInterviewListUseCase.execute(interviewPageRequest,httpRequest));
    }

    @Operation(summary = "사용자 인터뷰 레포트 조회", description = "인터뷰 및 평가 데이터를 조회합니다.")
    @GetMapping("/{id}")
    public SuccessResponse<InterviewReportResponse> getInterviewReport(@PathVariable Long id,  HttpServletRequest httpRequest) {
        return SuccessResponse.of(readInterviewReportUseCase.execute(id,httpRequest));
    }
}




