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
public class GPA {

    @Id
    @Column(name = "gpa_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long gpaId;

    @Column(name = "score")
    private String score;

    @Column(name = "total")
    private String total;

    @OneToOne
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;

    public void updateScore(String score) {
        this.score = score;
    }

    public void updateTotal(String total) {
        this.total = total;
    }

}
