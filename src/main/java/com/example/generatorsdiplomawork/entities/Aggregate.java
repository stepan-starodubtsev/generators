package com.example.generatorsdiplomawork.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "aggregates")
public class Aggregate {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @ManyToOne(fetch = FetchType.EAGER)
    private Unit unit;
    private String manufacturerNumber;
    @Enumerated(EnumType.STRING)
    private CategoryType categoryType;
    private LocalDateTime manufacturingDate;
    private LocalDateTime commissioningDate;
    @OneToOne(fetch = FetchType.EAGER)
    private Chassis chassis;
    @OneToOne(fetch = FetchType.EAGER)
    private Generator generator;
    @OneToOne(fetch = FetchType.EAGER)
    private Engine engine;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<WorkSheet> workSheets;

    public Aggregate(String name, Unit unit, String manufacturerNumber, CategoryType categoryType,
                     LocalDateTime manufacturingDate, LocalDateTime commissioningDate, Chassis chassis,
                     Generator generator, Engine engine, List<WorkSheet> workSheets) {
        this.name = name;
        this.unit = unit;
        this.manufacturerNumber = manufacturerNumber;
        this.categoryType = categoryType;
        this.manufacturingDate = manufacturingDate;
        this.commissioningDate = commissioningDate;
        this.chassis = chassis;
        this.generator = generator;
        this.engine = engine;
        this.workSheets = workSheets;
    }

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
        if (workSheets.isEmpty()) {
            return new WorkSheet();
        }
        return workSheets.get(workSheets.size() - 1);
    }

    public String getFuelByFuelType(String fuelType, String fuelState) {
        if (fuelType.equals(engine.getEngineType().getFuelType().getName())) {
            switch (fuelState){
                case "start":
                    return getLastWorkSheet().getStartFuelBalance().toString();
                case "fact":
                    return getLastWorkSheet().getFactFuelBalance().toString();
                case "obtain":
                    return getLastWorkSheet().getObtainedFuelSum().toString();
                case "used":
                    return getLastWorkSheet().getUsedFuel().toString();
            }
        }
        return "";
    }
}
