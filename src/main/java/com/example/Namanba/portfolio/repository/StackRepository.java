package com.example.Namanba.portfolio.repository;

import com.example.Namanba.portfolio.entity.Portfolio;
import com.example.Namanba.portfolio.entity.subitems.Stack;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StackRepository extends JpaRepository<Stack, Long> {
    List<Stack> findByPortfolio(Portfolio portfolio);
}
