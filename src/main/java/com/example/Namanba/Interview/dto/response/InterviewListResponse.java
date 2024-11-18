package com.example.Namanba.Interview.dto.response;

import com.example.Namanba.Interview.entity.Interview;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
@Builder
@AllArgsConstructor
public class InterviewListResponse {
    private Long interviewId;
    private String interviewTitle;
    private String positionName;
    private String createdDate;

    public static InterviewListResponse of(Interview interview){
        return InterviewListResponse.builder()
                .interviewId(interview.getInterviewId())
                .interviewTitle(interview.getInterviewTitle())
                .positionName(interview.getPositionName())
                .createdDate(interview.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")))
                .build();
    }
}

