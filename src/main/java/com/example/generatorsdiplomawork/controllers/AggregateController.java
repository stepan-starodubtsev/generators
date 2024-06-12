package com.example.generatorsdiplomawork.controllers;

import com.example.generatorsdiplomawork.entities.*;
import com.example.generatorsdiplomawork.repositories.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/aggregates/")
public class AggregateController {
    private final UnitRepository unitRepository;
    private final ChassisTypeRepository chassisTypeRepository;
    private final GeneratorTypeRepository generatorTypeRepository;
    private final ElectricCurrentTypeRepository electricCurrentTypeRepository;
    private final EngineTypeRepository engineTypeRepository;

    private List<Unit> units;
    private List<ChassisType> chassisTypes;
    private List<GeneratorType> generatorTypes;
    private List<ElectricCurrentType> electricCurrentTypes;
    private List<EngineType> engineTypes;

    public AggregateController(UnitRepository unitRepository, ChassisTypeRepository chassisTypeRepository, GeneratorTypeRepository generatorTypeRepository, ElectricCurrentTypeRepository electricCurrentTypeRepository, EngineTypeRepository engineTypeRepository) {
        this.unitRepository = unitRepository;
        units = unitRepository.findAll();
        this.chassisTypeRepository = chassisTypeRepository;
        this.generatorTypeRepository = generatorTypeRepository;
        this.electricCurrentTypeRepository = electricCurrentTypeRepository;
        this.engineTypeRepository = engineTypeRepository;
    }

    @GetMapping("create")
    public String create(Model model) {
        model.addAttribute("isCreating", true);
        model.addAttribute("units", units);
        model.addAttribute("chassisTypes", chassisTypes);
        model.addAttribute("generatorTypes", generatorTypes);
        model.addAttribute("electricCurrentTypes", electricCurrentTypes);
        model.addAttribute("engineTypes", engineTypes);
        return "aggregatePage";
    }
}
