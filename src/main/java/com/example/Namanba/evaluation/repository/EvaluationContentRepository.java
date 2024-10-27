package com.example.Namanba.evaluation.repository;

import com.example.Namanba.evaluation.entity.Category;
import com.example.Namanba.evaluation.entity.CategoryDetails;
import com.example.Namanba.evaluation.entity.EvaluationContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EvaluationContentRepository extends JpaRepository<EvaluationContent, Long> {

    EvaluationContent findByCategoryAndCriteria(String category, String criteria);
    EvaluationContent findByCategoryAndCategoryDetailsAndCriteria(Category category, CategoryDetails categoryDetails, String criteria);
}

