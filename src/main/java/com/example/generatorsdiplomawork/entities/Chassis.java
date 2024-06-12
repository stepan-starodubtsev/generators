package com.example.generatorsdiplomawork.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "chassis")
public class Chassis {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    private ChassisType type;
    private String number;

    public Chassis(ChassisType type, String number) {
        this.type = type;
        this.number = number;
    }
}
