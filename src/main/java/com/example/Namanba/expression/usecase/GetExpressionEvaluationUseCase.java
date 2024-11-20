package com.example.Namanba.expression.usecase;

import com.example.Namanba.Interview.adaptor.InterviewAdaptor;
import com.example.Namanba.Interview.entity.Interview;
import com.example.Namanba.common.annotation.UseCase;
import com.example.Namanba.evaluation.service.EvaluationDomainService;
import com.example.Namanba.expression.dto.response.ExpressionEvaluationDto;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class GetExpressionEvaluationUseCase {
    private final InterviewAdaptor interviewAdaptor;

    private final EvaluationDomainService evaluationDomainService;

    public ExpressionEvaluationDto execute(Long interviewId){

        Interview interview = interviewAdaptor.findByInterviewId(interviewId);

        return evaluationDomainService.getExpressionEvaluation(interview);
    }
}
