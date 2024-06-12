package com.example.generatorsdiplomawork.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private Double obtainedFuel;
    private Double obtainedOil;
}
