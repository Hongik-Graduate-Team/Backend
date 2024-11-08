package com.example.Namanba.audio.controller;

import com.example.Namanba.audio.usecase.AnalyzeAudioUseCase;
import com.example.Namanba.audio.usecase.processor.AudioEvaluationProcessor;
import com.example.Namanba.common.response.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/{interviewId}/audio")
@Tag(name = "사용자 음성 평가 API", description = "사용자의 음성(침묵 시간, 발화 속도, 목소리 크기)을 평가하는 API 입니다.")
public class AudioController {

    private final AnalyzeAudioUseCase analyzeAudioUseCase;

    private final AudioEvaluationProcessor audioEvaluationProcessor;

    @Operation(summary = "면접자의 음성 데이터를 받아온 후 평가합니다.")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public SuccessResponse<Void> getAudioData(
            @Parameter(
                    description = "음성 파일 (오디오 형식)",
                    required = true,
                    content = @Content(
                            mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
                            schema = @Schema(type = "string", format = "binary")
                    )
            )
            @RequestParam("audio") MultipartFile audioFile,
            @PathVariable("interviewId") Long interviewId
    ) {
        analyzeAudioUseCase.execute(audioFile); //음성 파일이 비어있는지 확인
        try {
            // 파일 저장 경로
            String fileName = audioFile.getOriginalFilename();
            Path path = Paths.get("uploads/" + fileName);

            // 디렉토리 존재 여부 확인 후 생성
            if (!Files.exists(path.getParent())) {
                Files.createDirectories(path.getParent());
            }

            // 파일 저장
            Files.write(path, audioFile.getBytes());

            // MultipartFile을 File로 변환
            File convertedFile = new File(path.toUri());

            audioEvaluationProcessor.processAudio(convertedFile);

        } catch (IOException e) {
            e.printStackTrace();
            // 적절한 예외 처리
            throw new RuntimeException("파일 처리 중 오류 발생", e);
        }


        return SuccessResponse.empty();
    }
}
