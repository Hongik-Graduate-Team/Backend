package com.example.Namanba.portfolio.entity.subitems;

import com.example.Namanba.portfolio.entity.Portfolio;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Career {

    @Id
    @Column(name = "career_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long careerId;

    @Column(name = "type")
    private String careerType;

    @Column(name = "content")
    private String content;

    // YYYY-MM-DD 형식
    @Column(name = "start_date")
    private LocalDate startDate;

    // YYYY-MM-DD 형식
    @Column(name = "end_date")
    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;

    public void updateCareerId(Long careerId) {
        this.careerId = careerId;
    }

    public void updateCareerType(String careerType) {
        this.careerType = careerType;
    }

    public void updateContent(String content) {
        this.content = content;
    }

    public void updateStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void updateEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

}
