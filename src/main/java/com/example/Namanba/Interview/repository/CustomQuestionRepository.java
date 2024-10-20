package com.example.Namanba.Interview.repository;

import com.example.Namanba.Interview.entity.CustomQuestion;
import com.example.Namanba.Interview.entity.Interview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomQuestionRepository extends JpaRepository<CustomQuestion, Long> {
    CustomQuestion findByInterview(Interview interview);
}
