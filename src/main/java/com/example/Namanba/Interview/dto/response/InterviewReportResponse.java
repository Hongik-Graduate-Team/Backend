package com.example.Namanba.Interview.dto.response;

import com.example.Namanba.Interview.entity.CustomQuestion;
import com.example.Namanba.Interview.entity.EvaluationStatus;
import com.example.Namanba.Interview.entity.Interview;
import com.example.Namanba.evaluation.entity.Evaluation;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InterviewReportResponse {
    private Long interviewId;
    private String interviewTitle;
    private String positionName;
    private String createdDate;

    private String basicInterview1;
    private String basicInterview2;
    private String basicInterview3;
    private String customQuestions;

    private String evaluationStatus;

    private Double gaze;
    private String gazeMessage;

    private Double expression;
    private String expressionMessage;

    private Double gesture;
    private String gestureMessage;

    private Double voiceVolume;
    private String voiceVolumeMessage;

    private Double speechRate;
    private String speechRateMessage;

    private Double silenceDuration;
    private String silenceDurationMessage;



    public static InterviewReportResponse of(Interview interview, Evaluation evaluation, CustomQuestion customQuestion) {
        EvaluationStatus status = EvaluationStatus.from(evaluation);

        InterviewReportResponseBuilder builder = InterviewReportResponse.builder()
                .interviewId(interview.getInterviewId())
                .interviewTitle(interview.getInterviewTitle())
                .positionName(interview.getPositionName())
                .createdDate(interview.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")))
                .basicInterview1(interview.getBasicInterview1())
                .basicInterview2(interview.getBasicInterview2())
                .basicInterview3(interview.getBasicInterview3())
                .customQuestions(customQuestion.getCustomQuestions())
                .evaluationStatus(status.getMessage());

        // evaluationStatus COMPLETED 상태일 때만 평가를 반환한다.
        if (status == EvaluationStatus.COMPLETED && evaluation != null) {
            builder
                    .gaze(evaluation.getGaze())
                    .gazeMessage(evaluation.getGazeMessage())
                    .expression(evaluation.getExpression())
                    .expressionMessage(evaluation.getExpressionMessage())
                    .gesture(evaluation.getGesture())
                    .gestureMessage(evaluation.getGestureMessage())
                    .voiceVolume(evaluation.getVoiceVolume())
                    .voiceVolumeMessage(evaluation.getVoiceVolumeMessage())
                    .speechRate(evaluation.getSpeechRate())
                    .speechRateMessage(evaluation.getSpeechRateMessage())
                    .silenceDuration(evaluation.getSilenceDuration())
                    .silenceDurationMessage(evaluation.getSilenceDurationMessage());
        }

        return builder.build();
    }
}