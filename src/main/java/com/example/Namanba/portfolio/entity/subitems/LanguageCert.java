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
public class LanguageCert {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "languagecert_id")
    private Long languageCertId;

    @Column(name = "type")
    private String languageCertType;

    @Column(name = "level")
    private String languageCertLevel;

    @Column(name = "date")
    private LocalDate languageCertDate;

    @ManyToOne
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;

    public void updateLanguageCertsType(String languageCertType) {
        this.languageCertType = languageCertType;
    }

    public void updateLanguageCertsLevel(String languageCertLevel) {
        this.languageCertLevel = languageCertLevel;
    }

    public void updateLanguageCertsDate(LocalDate languageCertDate) {
        this.languageCertDate = languageCertDate;
    }

}