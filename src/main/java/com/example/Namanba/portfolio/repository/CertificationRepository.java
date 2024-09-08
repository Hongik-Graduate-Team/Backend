package com.example.Namanba.portfolio.repository;

import com.example.Namanba.portfolio.entity.Portfolio;
import com.example.Namanba.portfolio.entity.subitems.Certification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CertificationRepository extends JpaRepository<Certification, Long>{
    List<Certification> findByPortfolio(Portfolio portfolio);
}
