package com.example.Namanba.portfolio.entity.subitems;

import com.example.Namanba.portfolio.entity.Portfolio;
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
public class Stack {

    @Id
    @Column(name = "stack_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long stackId;

    @Column(name = "language")
    private String stackLanguage;

    @Column(name = "level")
    private String stackLevel;

    @ManyToOne
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;

    public void updateStackLanguage(String stackLanguage) {
        this.stackLanguage = stackLanguage;
    }

    public void updateStackLevel(String stackLevel) {
        this.stackLevel = stackLevel;
    }
}

