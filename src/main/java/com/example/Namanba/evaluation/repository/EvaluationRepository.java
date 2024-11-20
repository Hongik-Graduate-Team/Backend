package com.example.Namanba.evaluation.repository;

import com.example.Namanba.Interview.entity.Interview;
import com.example.Namanba.evaluation.entity.Evaluation;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {

    Evaluation findByInterview(Interview interview);

    @Query("SELECT e FROM Evaluation e WHERE e.interview.interviewId = :interviewId")
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Evaluation> findWithLockByInterviewId(@Param("interviewId") Long interviewId);


}
