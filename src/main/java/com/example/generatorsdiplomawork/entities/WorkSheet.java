package com.example.generatorsdiplomawork.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.lang.annotation.Documented;
import java.time.LocalDateTime;
import java.util.List;
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
    private Double factFuelBalance;
    private Double factOilBalance;
    private Double usedFuel;
    private Double usedOil;
    @OneToMany(fetch = FetchType.EAGER)
    private List<WorkSheetStub> workSheetStubs;

    public WorkSheet(Double startFuelBalance,
                     Double startOilBalance, Stack<WorkSheetStub> workSheetStubs) {
        this.creatingDate = LocalDateTime.now();
        this.fromBeginningMonthWork = 0.0;
        this.startFuelBalance = startFuelBalance;
        this.startOilBalance = startOilBalance;
        this.factFuelBalance = startFuelBalance;
        this.factOilBalance = startOilBalance;
        this.obtainedFuelSum = 0.0;
        this.obtainedOilSum = 0.0;
        this.usedFuel = 0.0;
        this.usedOil = 0.0;
        this.workSheetStubs = workSheetStubs;
    }

    public void obtainFuel(Double fuelCount) {
        obtainedFuelSum += fuelCount;
        factFuelBalance += fuelCount;
    }

    public void useFuel(Double fuelCount) {
        factFuelBalance -= fuelCount;
        usedFuel += fuelCount;
    }

    public void obtainOil(Double oilCount) {
        obtainedOilSum += oilCount;
        factOilBalance += oilCount;
    }

    public void useOil(Double oilCount) {
        factOilBalance -= oilCount;
        usedOil += oilCount;
    }

    public void doWork(Double hoursOfWork) {
        fromBeginningMonthWork += hoursOfWork;
    }
}