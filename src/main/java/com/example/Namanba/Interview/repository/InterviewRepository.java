package com.example.Namanba.Interview.repository;

import com.example.Namanba.Interview.entity.Interview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterviewRepository extends JpaRepository<Interview, Long> {
    Interview findByInterviewId(Long interviewId);
}
