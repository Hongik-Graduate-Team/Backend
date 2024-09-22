package com.example.Namanba.Interview.repository;

import com.example.Namanba.Interview.entity.CustomQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomQuestionRepository extends JpaRepository<CustomQuestion, Long> {
}
