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
public class Award {

    @Id
    @Column(name = "award_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long awardId;

    @Column(name = "type")
    private String awardType;

    @Column(name = "prize")
    private String awardPrize;

    @ManyToOne
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;

    public void updateAwardType(String awardType) {
        this.awardType = awardType;
    }

    public void updateAwardPrize(String awardPrize) {
        this.awardPrize = awardPrize;
    }


}
