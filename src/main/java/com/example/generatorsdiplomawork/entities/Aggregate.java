package com.example.generatorsdiplomawork.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "aggregates")
public class Aggregate {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    private Unit unit;
    private String manufacturerNumber;
    @Enumerated(EnumType.STRING)
    private CategoryType categoryType;
    private LocalDateTime manufacturingDate;
    private LocalDateTime commissioningDate;
    @ManyToOne(fetch = FetchType.LAZY)
    private Chassis chassis;
    @ManyToOne(fetch = FetchType.LAZY)
    private Generator generator;
    @ManyToOne(fetch = FetchType.LAZY)
    private Engine engine;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<WorkSheet> workSheets;


    public String calculateCommissioningAge() {
        LocalDateTime age = LocalDateTime.now();
        age = age.minusYears(manufacturingDate.getYear());
        age = age.minusMonths(manufacturingDate.getMonthValue());
        age = age.minusDays(manufacturingDate.getDayOfMonth());
        if (age.getYear() == 0) {
            return age.getMonthValue() + "міс";
        } else {
            return age.getYear() + "р" + age.getMonthValue() + "міс";
        }
    }

    public WorkSheet getLastWorkSheet() {
        return workSheets.get(workSheets.size() - 1);
    }
}