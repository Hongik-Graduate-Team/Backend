package com.example.Namanba.portfolio.repository;

import com.example.Namanba.portfolio.entity.subitems.Position;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PositionRepository extends JpaRepository<Position, Long> {
    Position findByPositionId(Long positionId);
    Optional<Position> findByPositionName(String name);
}
