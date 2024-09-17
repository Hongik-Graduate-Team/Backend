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
public class Certification {

    @Id
    @Column(name = "cert_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long certId;

    @Column(name = "type")
    private String certType;

    @Column(name = "date")
    private LocalDate certDate;

    @ManyToOne
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;

    public void updateCertType(String certType) {
        this.certType = certType;
    }

    public void updateCertDate(LocalDate certDate) {
        this.certDate = certDate;
    }

}
