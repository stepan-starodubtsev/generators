package com.example.generatorsdiplomawork.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Stack;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "work_sheets")
public class WorkSheet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDateTime creatingDate;
    private Double fromBeginningMonthWork;
    private Double startFuelBalance;
    private Double startOilBalance;
    private Double obtainedFuelSum;
    private Double obtainedOilSum;
    private Double usedFuel;
    private Double usedOil;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Stack<WorkSheetStub> workSheetStubs;

    public WorkSheet(Double fromBeginningMonthWork, Double startFuelBalance,
                     Double startOilBalance, Double obtainedFuelSum, Double obtainedOilSum, Double usedFuel,
                     Double usedOil, Stack<WorkSheetStub> workSheetStubs) {
        this.creatingDate = LocalDateTime.now();
        this.fromBeginningMonthWork = fromBeginningMonthWork;
        this.startFuelBalance = startFuelBalance;
        this.startOilBalance = startOilBalance;
        this.obtainedFuelSum = obtainedFuelSum;
        this.obtainedOilSum = obtainedOilSum;
        this.usedFuel = usedFuel;
        this.usedOil = usedOil;
        this.workSheetStubs = workSheetStubs;
    }

    public Double getFactStartFuelBalance() {
        return startFuelBalance + obtainedFuelSum;
    }

    public Double getFactStartOilBalance() {
        return startOilBalance + obtainedOilSum;
    }

    public Double getEndFuelBalance() {
        return getFactStartFuelBalance() - getUsedFuel();
    }

    public Double getEndOilBalance() {
        return getFactStartOilBalance() - getUsedOil();
    }
}