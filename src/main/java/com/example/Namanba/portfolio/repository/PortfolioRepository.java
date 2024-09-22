package com.example.Namanba.portfolio.repository;

import com.example.Namanba.portfolio.entity.Portfolio;
import com.example.Namanba.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
    Portfolio findByUser(User user);

    Optional<Portfolio> findByPortfolioId(Long portfolioId);

}
