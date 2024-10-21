package com.example.Namanba.evaluation.repository;

import com.example.Namanba.Interview.entity.Interview;
import com.example.Namanba.evaluation.entity.Evaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {

    Evaluation findByInterview(Interview interview);
}
