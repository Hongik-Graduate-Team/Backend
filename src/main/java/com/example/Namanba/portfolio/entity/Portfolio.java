package com.example.Namanba.portfolio.entity;

import com.example.Namanba.portfolio.entity.subitems.Position;
import com.example.Namanba.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Portfolio {

    @Id
    @Column(name = "portfolio_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long portfolioId;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "position_id", nullable = true)
    private Position position;

    public void updatePosition(Position position) {
        this.position = position;
    }

}
