package com.example.Namanba.portfolio.repository;

import com.example.Namanba.portfolio.entity.Portfolio;
import com.example.Namanba.portfolio.entity.subitems.GPA;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GPARepository extends JpaRepository<GPA, Long> {
    List<GPA> findByPortfolio(Portfolio portfolio);
}
