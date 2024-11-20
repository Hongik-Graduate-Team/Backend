package com.example.Namanba.evaluation.adaptor;

import com.example.Namanba.Interview.entity.Interview;
import com.example.Namanba.common.annotation.Adaptor;
import com.example.Namanba.common.exception.base.BaseException;
import com.example.Namanba.evaluation.entity.Evaluation;
import com.example.Namanba.evaluation.exception.EvaluationErrorCode;
import com.example.Namanba.evaluation.repository.EvaluationRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@Adaptor
@RequiredArgsConstructor
public class EvaluationAdaptor {
    private final EvaluationRepository evaluationRepository;
    public Evaluation save(Evaluation evaluation) {
        return evaluationRepository.save(evaluation);
    }
    public Evaluation findByInterview(Interview interview) {
        return evaluationRepository.findWithLockByInterviewId(interview.getInterviewId())
                .orElseThrow(() -> new BaseException(EvaluationErrorCode.EVALUATION_NOT_FOUND));
    }

    public Optional<Evaluation> findByOptionalInterview(Interview interview) {
        return evaluationRepository.findWithLockByInterviewId(interview.getInterviewId());
    }

}
