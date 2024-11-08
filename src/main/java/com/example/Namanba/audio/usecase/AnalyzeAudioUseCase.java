package com.example.Namanba.audio.usecase;

import com.example.Namanba.common.annotation.UseCase;
import com.example.Namanba.common.exception.CustomException;
import com.example.Namanba.common.exception.GlobalErrorCode;
import com.example.Namanba.common.exception.base.BaseException;
import com.example.Namanba.evaluation.exception.EvaluationErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

@UseCase
@RequiredArgsConstructor
public class AnalyzeAudioUseCase {
    public ResponseEntity<Void> execute(MultipartFile audioFile) {
        // 파일이 비어있는지 확인
        if (audioFile.isEmpty()) {
            // 음성 파일이 비어있으면 AUDIO_NOT_EXIST 에러 던지기
            throw new BaseException(EvaluationErrorCode.AUDIO_NOT_EXIST);
        }
        return ResponseEntity.ok().build();
    }
}
