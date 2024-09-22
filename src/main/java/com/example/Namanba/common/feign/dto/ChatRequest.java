package com.example.Namanba.common.feign.dto;

import com.example.Namanba.common.annotation.Adaptor;
import com.google.protobuf.Message;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ChatRequest {
    private String model;
    private List<Message> messages; //시스템 메시지, 사용자 메시지, AI 이전 응답을 보낼 수 있음
    @Data
    @AllArgsConstructor
    public static class Message{
        private String role;
        private String content;
    }

}
