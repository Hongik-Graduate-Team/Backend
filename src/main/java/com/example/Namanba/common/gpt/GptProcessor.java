package com.example.Namanba.common.gpt;


import com.example.Namanba.Interview.dto.PromptContent;
import com.example.Namanba.common.annotation.Processor;
import com.example.Namanba.common.feign.GptApi;
import com.example.Namanba.common.feign.dto.ChatRequest;
import com.example.Namanba.common.feign.dto.ChatResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

@Processor
@RequiredArgsConstructor
public class GptProcessor {

    @Value("${chatgpt.api-key}")
    private String apiKey;

    @Value("${chatgpt.model}")
    private String gptModel;

    private final GptApi gptApi;

    public ChatResponse callChatGpt(PromptContent promptContent){
        ChatRequest requestBody = new ChatRequest(
                gptModel,
                List.of(
                        new ChatRequest.Message("system",promptContent.getSystemMessage()),
                        new ChatRequest.Message("user", promptContent.getUserMessage())
                )
        );

        return gptApi.callChatGpt("Bearer " + apiKey, "application/json", requestBody);
    }
}
