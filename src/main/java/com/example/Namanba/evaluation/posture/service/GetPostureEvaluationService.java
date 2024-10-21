package com.example.Namanba.evaluation.posture.service;

import com.example.Namanba.common.util.JwtUtil;
import com.example.Namanba.evaluation.adaptor.EvaluationAdaptor;
import com.example.Namanba.evaluation.posture.dto.response.PostureResultDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetPostureEvaluationService {
    private final JwtUtil jwtUtil;
    private final EvaluationAdaptor evaluationAdaptor;

    public PostureResultDto execute(Long interviewId){
        evaluationAdaptor.findBy
    }

}
