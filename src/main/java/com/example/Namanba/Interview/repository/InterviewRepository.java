package com.example.Namanba.Interview.repository;

import com.example.Namanba.Interview.entity.Interview;
import com.example.Namanba.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface InterviewRepository extends JpaRepository<Interview, Long> {
    boolean existsByInterviewId(Long interviewId);
    Optional<Interview> findByInterviewIdAndUser(Long interviewId, User user);
//    Page<Interview> findByUser(User user, Pageable pageable);

    @Query("SELECT i FROM Interview i WHERE i.user = :user AND EXISTS (" +
            "SELECT c FROM CustomQuestion c WHERE c.interview = i)")
    Page<Interview> findWithCustomQuestionsByUser(@Param("user") User user, Pageable pageable);

    Interview findByInterviewId(Long interviewId);

}
