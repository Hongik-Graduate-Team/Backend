package com.example.Namanba.audio.usecase;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class AudioStorageUseCase {

    private static final String UPLOAD_DIRECTORY = "uploads/";

    public File execute(MultipartFile audioFile) {
        try {
            String fileName = audioFile.getOriginalFilename();
            Path path = Paths.get(UPLOAD_DIRECTORY + fileName);

            // 디렉토리가 없으면 생성
            if (!Files.exists(path.getParent())) {
                Files.createDirectories(path.getParent());
            }

            // 파일 저장
            Files.write(path, audioFile.getBytes());


            return new File(path.toUri());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("파일 저장 중 오류 발생", e);
        }
    }
}

