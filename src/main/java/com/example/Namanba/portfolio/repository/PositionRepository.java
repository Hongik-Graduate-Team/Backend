package com.example.Namanba.portfolio.repository;

import com.example.Namanba.portfolio.entity.subitems.Position;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PositionRepository extends JpaRepository<Position, Long> {
    Position findByPositionId(Long positionId);
    Position findByPositionName(String name);
}
