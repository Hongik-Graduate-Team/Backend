package com.example.Namanba.evaluation.adaptor;

import com.example.Namanba.common.annotation.Adaptor;
import com.example.Namanba.evaluation.repository.EvaluationRepository;
import lombok.RequiredArgsConstructor;

@Adaptor
@RequiredArgsConstructor
public class EvaluationAdaptor {
    private final EvaluationRepository evaluationRepository;


}
