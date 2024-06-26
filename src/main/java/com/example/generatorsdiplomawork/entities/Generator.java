package com.example.generatorsdiplomawork.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "generators")
public class Generator {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    private GeneratorType generatorType;
    private String number;
    private Double power;
    @ManyToOne(fetch = FetchType.EAGER)
    private ElectricCurrentType electricCurrentType;

    public Generator(GeneratorType generatorType, String number, Double power, ElectricCurrentType electricCurrentType) {
        this.generatorType = generatorType;
        this.number = number;
        this.power = power;
        this.electricCurrentType = electricCurrentType;
    }
}
