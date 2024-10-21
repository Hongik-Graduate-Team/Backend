package com.example.Namanba.evaluation.posture.service;

import com.example.Namanba.evaluation.adaptor.EvaluationAdaptor;
import com.example.Namanba.evaluation.posture.dto.request.PostureDataDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EvaluatePostureService {
    private final EvaluationAdaptor evaluationAdaptor;
    public void execute(Long interviewId, PostureDataDto postureDataDto){

    }

}
