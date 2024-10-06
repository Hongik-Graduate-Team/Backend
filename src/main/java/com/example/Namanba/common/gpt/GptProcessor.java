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
    public ChatResponse callChatGpt(PromptContent promptContent) {
        ChatRequest requestBody = new ChatRequest(
                gptModel,
                List.of(
                        new ChatRequest.Message("system", promptContent.getSystemMessage()),
                        new ChatRequest.Message("user", promptContent.getUserMessage())
                )
        );

        // API 호출
        ChatResponse response = gptApi.callChatGpt("Bearer " + apiKey, requestBody);

        // 응답 출력
        if (response != null) {
            ChatResponse.Choice.Message firstChoiceMessage = response.getFirstChoiceMessage(); // 수정된 부분
            if (firstChoiceMessage != null) {
                System.out.println("Role: " + firstChoiceMessage.getRole());
                System.out.println("Content: " + firstChoiceMessage.getContent());
            } else {
                System.out.println("First choice message is null.");
            }
        } else {
            System.out.println("Response is null.");
        }

        return response;
    }
}