package com.example.Namanba.common.feign;

import com.example.Namanba.common.feign.dto.ChatRequest;
import com.example.Namanba.common.feign.dto.ChatResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(
        name = "GPTApi",
        url = "https://api.openai.com/v1"
)
public interface GptApi {
    @PostMapping("/chat/completions")
    ChatResponse callChatGpt(
            @RequestHeader("Authorization") String authHeader,
            @RequestHeader("Content-Type") String contentType,
            @RequestBody ChatRequest requestBody);
}
