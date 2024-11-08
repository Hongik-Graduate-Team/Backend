package com.example.Namanba.audio.controller;

import com.example.Namanba.common.response.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/{interviewId}/audio")
@Tag(name = "사용자 음성 평가 API", description = "사용자의 음성(침묵 시간, 발화 속도, 목소리 크기)을 평가하는 API 입니다.")
public class AudioController {

    @Operation(summary = "면접자의 음성 데이터를 받아온 후 평가합니다.")
    @PostMapping
    public SuccessResponse<Void> getAudioData(@RequestParam("audio") MultipartFile audioFile, @PathVariable("interviewId") Long interviewId) {
//        if (audioFile.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("파일이 비어 있습니다.");
//        }
//
//        try {
//            // 오디오 파일 분석을 위한 서비스 호출
//            String analysisResult = audioAnalysisService.analyzeAudio(audioFile);
//            return ResponseEntity.ok("오디오 파일 분석 결과: " + analysisResult);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("오디오 파일 분석 중 오류 발생: " + e.getMessage());
//        }
        return SuccessResponse.empty();
    }
}
