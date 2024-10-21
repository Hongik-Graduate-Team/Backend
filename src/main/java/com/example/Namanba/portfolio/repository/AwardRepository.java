package com.example.Namanba.portfolio.repository;

import com.example.Namanba.portfolio.entity.Portfolio;
import com.example.Namanba.portfolio.entity.subitems.Award;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface AwardRepository extends JpaRepository<Award, Long> {
    List<Award> findByPortfolio(Portfolio portfolio);
}
