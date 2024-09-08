package com.example.Namanba.portfolio.repository;

import com.example.Namanba.portfolio.entity.Portfolio;
import com.example.Namanba.portfolio.entity.subitems.LanguageCerts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LanguageCertsRepository extends JpaRepository<LanguageCerts, Long> {
    List<LanguageCerts> findByPortfolio(Portfolio portfolio);
}
