package com.example.generatorsdiplomawork.controllers;

import com.example.generatorsdiplomawork.entities.*;
import com.example.generatorsdiplomawork.repositories.*;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/aggregates/")
public class AggregateController {
    private final AggregateRepository aggregateRepository;
    private final UnitRepository unitRepository;
    private final ChassisTypeRepository chassisTypeRepository;
    private final ChassisRepository chassisRepository;
    private final GeneratorTypeRepository generatorTypeRepository;
    private final GeneratorRepository generatorRepository;
    private final ElectricCurrentTypeRepository electricCurrentTypeRepository;
    private final EngineTypeRepository engineTypeRepository;
    private final EngineRepository engineRepository;

    private List<Unit> units;
    private List<ChassisType> chassisTypes;
    private List<GeneratorType> generatorTypes;
    private List<ElectricCurrentType> electricCurrentTypes;
    private List<EngineType> engineTypes;

    public AggregateController(AggregateRepository aggregateRepository, UnitRepository unitRepository, ChassisTypeRepository chassisTypeRepository, ChassisRepository chassisRepository, GeneratorTypeRepository generatorTypeRepository, GeneratorRepository generatorRepository, ElectricCurrentTypeRepository electricCurrentTypeRepository, EngineTypeRepository engineTypeRepository, EngineRepository engineRepository) {
        this.aggregateRepository = aggregateRepository;
        this.unitRepository = unitRepository;
        this.chassisTypeRepository = chassisTypeRepository;
        this.chassisRepository = chassisRepository;
        this.generatorTypeRepository = generatorTypeRepository;
        this.generatorRepository = generatorRepository;
        this.electricCurrentTypeRepository = electricCurrentTypeRepository;
        this.engineTypeRepository = engineTypeRepository;

        units = unitRepository.findAll();
        chassisTypes = chassisTypeRepository.findAll();
        generatorTypes = generatorTypeRepository.findAll();
        electricCurrentTypes = electricCurrentTypeRepository.findAll();
        engineTypes = engineTypeRepository.findAll();
        this.engineRepository = engineRepository;
    }

    @GetMapping("create")
    public String createPage(Model model) {
        model.addAttribute("actionPath", "/aggregates/create");
        model.addAttribute("isCreating", true);
        model.addAttribute("aggregateName", "");
        model.addAttribute("aggregateManufacturerNumber", "");
        model.addAttribute("aggregateManufacturingDate", "");
        model.addAttribute("aggregateCommissioningDate", "");
        model.addAttribute("aggregateChassisNumber", "");
        model.addAttribute("aggregateGeneratorNumber", "");
        model.addAttribute("aggregateGeneratorPower", "");
        model.addAttribute("aggregateEngineNumber", "");
        model.addAttribute("aggregateFromBeginningWork", "");

        model.addAttribute("units", units);
        model.addAttribute("chassisTypes", chassisTypes);
        model.addAttribute("generatorTypes", generatorTypes);
        model.addAttribute("electricCurrentTypes", electricCurrentTypes);
        model.addAttribute("engineTypes", engineTypes);
        List<CategoryType> categoryTypes = new ArrayList<>(Arrays.stream(CategoryType.values()).toList());
        model.addAttribute("categoryTypes", categoryTypes);

        return "aggregatePage";
    }

    @PostMapping("create")
    public String create(@RequestParam String name,
                         @RequestParam String manufactureNumber,
                         @RequestParam String category,
                         @RequestParam String unitId,
                         @RequestParam String manufactureDate,
                         @RequestParam String commissioningDate,
                         @RequestParam String chassisTypeId,
                         @RequestParam String chassisNumber,
                         @RequestParam String generatorTypeId,
                         @RequestParam String generatorNumber,
                         @RequestParam String power,
                         @RequestParam String electricCurrentId,
                         @RequestParam String engineTypeId,
                         @RequestParam String engineNumber,
                         @RequestParam String fromBeginningWork){

        Unit unit = unitRepository.findById(Long.parseLong(unitId)).get();

        ChassisType chassisType = chassisTypeRepository.findById(Long.parseLong(chassisTypeId)).get();
        Chassis chassis = new Chassis(chassisType, chassisNumber);
        chassisTypeRepository.save(chassisType);
        chassisRepository.save(chassis);

        GeneratorType generatorType = generatorTypeRepository.findById(Long.parseLong(generatorTypeId)).get();
        generatorTypeRepository.save(generatorType);
        ElectricCurrentType electricCurrentType =
                electricCurrentTypeRepository.findById(Long.parseLong(electricCurrentId)).get();
        electricCurrentTypeRepository.save(electricCurrentType);
        Generator generator =
                new Generator(generatorType, generatorNumber, Double.parseDouble(power), electricCurrentType);
        generatorRepository.save(generator);

        EngineType engineType = engineTypeRepository.findById(Long.parseLong(engineTypeId)).get();
        Engine engine = new Engine(engineType, engineNumber, Double.parseDouble(fromBeginningWork));
        engineTypeRepository.save(engineType);
        engineRepository.save(engine);

        Aggregate aggregate = new Aggregate(name, unit, manufactureNumber, CategoryType.valueOf(category),
                LocalDateTime.parse(manufactureDate), LocalDateTime.parse(commissioningDate), chassis, generator,
                engine, new ArrayList<>());
        Aggregate savedAggregate = aggregateRepository.save(aggregate);
        return "redirect:/aggregates/edit/" + savedAggregate.getId();
    }

    @GetMapping("edit/{id}")
    public String editPage(@PathVariable Long id, Model model) {
        Aggregate aggregate = aggregateRepository.findById(id).get();

        model.addAttribute("aggregate", aggregate);
        model.addAttribute("actionPath", "/aggregates/edit/" + id);
        model.addAttribute("isCreating", false);
        model.addAttribute("aggregateName", aggregate.getName());
        model.addAttribute("aggregateManufacturerNumber", aggregate.getManufacturerNumber());
        model.addAttribute("aggregateManufacturingDate", aggregate.getManufacturingDate());
        model.addAttribute("aggregateCommissioningDate", aggregate.getCommissioningDate());
        model.addAttribute("aggregateChassisNumber", aggregate.getChassis().getNumber());
        model.addAttribute("aggregateGeneratorNumber", aggregate.getGenerator().getNumber());
        model.addAttribute("aggregateGeneratorPower", aggregate.getGenerator().getPower());
        model.addAttribute("aggregateEngineNumber", aggregate.getEngine().getNumber());
        model.addAttribute("aggregateFromBeginningWork", aggregate.getEngine().getFromBeginningWork());

        units.remove(aggregate.getUnit());
        List<Unit> unitsTmp = new ArrayList<>();
        unitsTmp.add(aggregate.getUnit());
        unitsTmp.addAll(units);
        model.addAttribute("units", unitsTmp);

        chassisTypes.remove(aggregate.getChassis().getType());
        List<ChassisType> chassisTypesTmp = new ArrayList<>();
        chassisTypesTmp.add(aggregate.getChassis().getType());
        chassisTypesTmp.addAll(chassisTypes);
        model.addAttribute("chassisTypes", chassisTypesTmp);

        generatorTypes.remove(aggregate.getGenerator().getGeneratorType());
        List<GeneratorType> generatorTypesTmp = new ArrayList<>();
        generatorTypesTmp.add(aggregate.getGenerator().getGeneratorType());
        generatorTypesTmp.addAll(generatorTypes);
        model.addAttribute("generatorTypes", generatorTypesTmp);

        electricCurrentTypes.remove(aggregate.getGenerator().getElectricCurrentType());
        List<ElectricCurrentType> electricCurrentTypesTmp = new ArrayList<>();
        electricCurrentTypesTmp.add(aggregate.getGenerator().getElectricCurrentType());
        electricCurrentTypesTmp.addAll(electricCurrentTypes);
        model.addAttribute("electricCurrentTypes", electricCurrentTypesTmp);

        engineTypes.remove(aggregate.getEngine().getEngineType());
        List<EngineType> engineTypesTmp = new ArrayList<>();
        engineTypesTmp.add(aggregate.getEngine().getEngineType());
        engineTypesTmp.addAll(engineTypes);
        model.addAttribute("engineTypes", engineTypesTmp);

        List<CategoryType> categoryTypes = new ArrayList<>(Arrays.stream(CategoryType.values()).toList());
        categoryTypes.remove(aggregate.getCategoryType());
        List<CategoryType> categoryTypesTmp = new ArrayList<>();
        categoryTypesTmp.add(aggregate.getCategoryType());
        categoryTypesTmp.addAll(categoryTypes);
        model.addAttribute("categoryTypes", categoryTypesTmp);
        return "aggregatePage";
    }

    @PostMapping("edit/{id}")
    public String edit(@PathVariable Long id,
                         @RequestParam String name,
                         @RequestParam String manufactureNumber,
                         @RequestParam String category,
                         @RequestParam String unitId,
                         @RequestParam String manufactureDate,
                         @RequestParam String commissioningDate,
                         @RequestParam String chassisTypeId,
                         @RequestParam String chassisNumber,
                         @RequestParam String generatorTypeId,
                         @RequestParam String generatorNumber,
                         @RequestParam String power,
                         @RequestParam String electricCurrentId,
                         @RequestParam String engineTypeId,
                         @RequestParam String engineNumber,
                         @RequestParam String fromBeginningWork){

        Unit unit = unitRepository.findById(Long.parseLong(unitId)).get();

        ChassisType chassisType = chassisTypeRepository.findById(Long.parseLong(chassisTypeId)).get();
        Chassis chassis = new Chassis(chassisType, chassisNumber);
        chassisTypeRepository.save(chassisType);
        chassisRepository.save(chassis);

        GeneratorType generatorType = generatorTypeRepository.findById(Long.parseLong(generatorTypeId)).get();
        generatorTypeRepository.save(generatorType);
        ElectricCurrentType electricCurrentType =
                electricCurrentTypeRepository.findById(Long.parseLong(electricCurrentId)).get();
        electricCurrentTypeRepository.save(electricCurrentType);
        Generator generator =
                new Generator(generatorType, generatorNumber, Double.parseDouble(power), electricCurrentType);
        generatorRepository.save(generator);

        EngineType engineType = engineTypeRepository.findById(Long.parseLong(engineTypeId)).get();
        Engine engine = new Engine(engineType, engineNumber, Double.parseDouble(fromBeginningWork));
        engineTypeRepository.save(engineType);
        engineRepository.save(engine);

        Aggregate aggregate = new Aggregate(id, name, unit, manufactureNumber, CategoryType.valueOf(category),
                LocalDateTime.parse(manufactureDate), LocalDateTime.parse(commissioningDate), chassis, generator,
                engine, new ArrayList<>());
        Aggregate updatedAggregate = aggregateRepository.save(aggregate);
        return "redirect:/aggregates/edit/" + updatedAggregate.getId();
    }

    @GetMapping("/delete/{id}")
    public String deleteAggregate(@PathVariable Long id){
        Aggregate aggregate = aggregateRepository.findById(id).get();
        aggregateRepository.delete(aggregate);
        return "redirect:/";
    }

    @GetMapping("calendar/{aggregateId}")
    public String calendar(@PathVariable Long aggregateId, Model model){
        Aggregate aggregate = aggregateRepository.findById(aggregateId).get();
        model.addAttribute("aggregate", aggregate);
        model.addAttribute("months", Month.values());
        int year = LocalDateTime.now().getYear();
        List<Integer> years = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            years.add(year++);
        }
        model.addAttribute("years", years);

        return "workCalendar";
    }

}
