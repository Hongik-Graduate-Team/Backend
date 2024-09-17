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
public class Major {

    @Id
    @Column(name="major_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long majorId;

    @Column(name = "majorname")
    private String majorName;

    @ManyToOne
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;

    public void updateMajorName(String majorName) {
        this.majorName = majorName;
    }

}
