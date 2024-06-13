package com.example.generatorsdiplomawork.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "work_sheet_stubs")
public class WorkSheetStub {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDateTime startWorkDateTime;
    private LocalDateTime endWorkDateTime;
    private String workDescription;
    private Double workHours;
    private Double obtainedFuel;
    private Double obtainedOil;
    private Double usedFuel;
    private Double usedOil;

    public WorkSheetStub(LocalDateTime startWorkDateTime, LocalDateTime endWorkDateTime, String workDescription,
                         Double obtainedFuel, Double obtainedOil, Double useFuelNorm, Double useOilNorm) {
        this.startWorkDateTime = startWorkDateTime;
        this.endWorkDateTime = endWorkDateTime;
        this.workDescription = workDescription;
        this.obtainedFuel = obtainedFuel;
        this.obtainedOil = obtainedOil;
        this.workHours = (double) Duration.between(startWorkDateTime, endWorkDateTime).toHours();
        this.usedFuel = calculateUsedFuel(useFuelNorm);
        this.usedOil = calculateUsedOil(useOilNorm);
    }

    private double calculateUsedFuel(Double useFuelNorm) {
        double workMinutes = workHours * 60;
        return (workMinutes * useFuelNorm) / 60;
    }

    private double calculateUsedOil(Double useFuelNorm) {
        return (usedFuel * useFuelNorm) / 100;
    }
}
