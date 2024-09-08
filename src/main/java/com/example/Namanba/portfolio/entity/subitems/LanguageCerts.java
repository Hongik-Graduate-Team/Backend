package com.example.Namanba.portfolio.entity.subitems;

import com.example.Namanba.portfolio.entity.Portfolio;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LanguageCerts {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "languagecert_id")
    private Long languageCertsId;

    @Column(name = "type")
    private String languageCertsType;

    @Column(name = "level")
    private String languageCertsLevel;

    @Column(name = "date")
    private LocalDate languageCertsDate;

    @ManyToOne
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;

    public void updateLanguageCertsType(String languageCertsType) {
        this.languageCertsType = languageCertsType;
    }

    public void updateLanguageCertsLevel(String languageCertsLevel) {
        this.languageCertsLevel = languageCertsLevel;
    }

    public void updateLanguageCertsDate(LocalDate languageCertsDate) {
        this.languageCertsDate = languageCertsDate;
    }

}

