package com.example.Namanba.Interview.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PromptContent {
    private String systemMessage;
    private String userMessage;
}
