package com.example.Namanba.common.feign.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChatResponse {
    private Message message;

    @Data
    @AllArgsConstructor
    public static class Message {
        private String role;
        private String content;
    }
}
