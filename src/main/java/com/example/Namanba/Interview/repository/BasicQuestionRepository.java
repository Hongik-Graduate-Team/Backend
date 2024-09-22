package com.example.Namanba.Interview.repository;

import com.example.Namanba.Interview.entity.BasicQuestion;
import com.example.Namanba.portfolio.entity.subitems.Position;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BasicQuestionRepository extends JpaRepository<BasicQuestion, Long> {
    List<BasicQuestion> findByPosition(Position position);
}
