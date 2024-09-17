package com.example.Namanba.portfolio.repository;

import com.example.Namanba.portfolio.entity.Portfolio;
import com.example.Namanba.portfolio.entity.subitems.LanguageCert;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LanguageCertRepository extends JpaRepository<LanguageCert, Long> {
    List<LanguageCert> findByPortfolio(Portfolio portfolio);
}