package com.example.Namanba.evaluation.adaptor;

import com.example.Namanba.common.annotation.Adaptor;
import com.example.Namanba.common.exception.base.BaseException;
import com.example.Namanba.evaluation.entity.Category;
import com.example.Namanba.evaluation.entity.CategoryDetails;
import com.example.Namanba.evaluation.entity.EvaluationContent;
import com.example.Namanba.evaluation.exception.EvaluationErrorCode;
import com.example.Namanba.evaluation.repository.EvaluationContentRepository;
import lombok.RequiredArgsConstructor;

// 각 평가 카테고리 별로 메시지가 저장되어 있는 repository
@Adaptor
@RequiredArgsConstructor
public class EvaluationContentAdaptor {
    private final EvaluationContentRepository evaluationContentRepository;
    public String fetchMessageByCriteria(Category category, CategoryDetails categoryDetails, String criteria){
        EvaluationContent evaluationContent =  evaluationContentRepository.findByCategoryAndCategoryDetailsAndCriteria(category,categoryDetails, criteria);
        if (evaluationContent == null){
            throw new BaseException(EvaluationErrorCode.MESSAGE_NOT_FOUND);
        }
        return evaluationContent.getMessage();
    }
}
