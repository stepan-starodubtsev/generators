package com.example.generatorsdiplomawork.controllers;

import com.example.generatorsdiplomawork.entities.Aggregate;
import com.example.generatorsdiplomawork.entities.FuelType;
import com.example.generatorsdiplomawork.entities.Month;
import com.example.generatorsdiplomawork.repositories.AggregateRepository;
import com.example.generatorsdiplomawork.utils.FormatUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
public class MainController {
    private final AggregateRepository aggregateRepository;

    public MainController(AggregateRepository aggregateRepository) {
        this.aggregateRepository = aggregateRepository;
    }

    @GetMapping
    public String index(Model model) {
        List<Aggregate> aggregates = aggregateRepository.findAll();

        model.addAttribute("aggregates", aggregates);
        model.addAttribute("formatUtils", new FormatUtils());
        return "index";
    }

    @PostMapping("search/aggregate/name")
    public String searchAggregateByName(@RequestParam String searchedName, Model model) {
        List<Aggregate> aggregates = aggregateRepository.findAll();
        List<Aggregate> searchedAggregate = aggregates.stream()
                .filter(x -> x.getName().toLowerCase().contains(searchedName.toLowerCase())).toList();
        model.addAttribute("aggregates", searchedAggregate);
        return "index";
    }

    @GetMapping("/fuelReport")
    public String getFuelReport(Model model) {
        List<Aggregate> aggregates = aggregateRepository.findAll().stream()
                .filter(aggregate -> !aggregate.getWorkSheets().isEmpty()).toList();

        model.addAttribute("aggregates", aggregates);
        model.addAttribute("monthNow", Month.values()[LocalDate.now().getMonthValue()]);
        model.addAttribute("sumWorkSheetValues", getSumLastWorkSheetValues(aggregates));
        model.addAttribute("formatUtils", new FormatUtils());

        return "fuelReport";
    }

    private List<Double> getSumLastWorkSheetValues(List<Aggregate> aggregates) {
        List<Double> sumWorkSheetValues = new ArrayList<>();

        sumWorkSheetValues.add(aggregates.stream()
                .filter(aggregate -> aggregate.getEngine().getEngineType().getFuelType().equals(FuelType.ДП))
                .map(aggregate -> aggregate.getLastWorkSheet().getStartFuelBalance())
                .reduce(0.0, Double::sum));

        sumWorkSheetValues.add(aggregates.stream()
                .filter(aggregate -> aggregate.getEngine().getEngineType().getFuelType().equals(FuelType.Бензин))
                .map(aggregate -> aggregate.getLastWorkSheet().getStartFuelBalance())
                .reduce(0.0, Double::sum));

        sumWorkSheetValues.add(aggregates.stream()
                .map(aggregate -> aggregate.getLastWorkSheet().getStartOilBalance())
                .reduce(0.0, Double::sum));

        sumWorkSheetValues.add(aggregates.stream()
                .filter(aggregate -> aggregate.getEngine().getEngineType().getFuelType().equals(FuelType.ДП))
                .map(aggregate -> aggregate.getLastWorkSheet().getObtainedFuelSum())
                .reduce(0.0, Double::sum));

        sumWorkSheetValues.add(aggregates.stream()
                .filter(aggregate -> aggregate.getEngine().getEngineType().getFuelType().equals(FuelType.Бензин))
                .map(aggregate -> aggregate.getLastWorkSheet().getObtainedFuelSum())
                .reduce(0.0, Double::sum));


        sumWorkSheetValues.add(aggregates.stream()
                .map(aggregate -> aggregate.getLastWorkSheet().getObtainedOilSum())
                .reduce(0.0, Double::sum));

        sumWorkSheetValues.add(aggregates.stream()
                .filter(aggregate -> aggregate.getEngine().getEngineType().getFuelType().equals(FuelType.ДП))
                .map(aggregate -> aggregate.getLastWorkSheet().getUsedFuel())
                .reduce(0.0, Double::sum));

        sumWorkSheetValues.add(aggregates.stream()
                .filter(aggregate -> aggregate.getEngine().getEngineType().getFuelType().equals(FuelType.Бензин))
                .map(aggregate -> aggregate.getLastWorkSheet().getUsedFuel())
                .reduce(0.0, Double::sum));


        sumWorkSheetValues.add(aggregates.stream()
                .map(aggregate -> aggregate.getLastWorkSheet().getUsedOil())
                .reduce(0.0, Double::sum));

        sumWorkSheetValues.add(aggregates.stream()
                .filter(aggregate -> aggregate.getEngine().getEngineType().getFuelType().equals(FuelType.ДП))
                .map(aggregate -> aggregate.getLastWorkSheet().getFactFuelBalance())
                .reduce(0.0, Double::sum));

        sumWorkSheetValues.add(aggregates.stream()
                .filter(aggregate -> aggregate.getEngine().getEngineType().getFuelType().equals(FuelType.Бензин))
                .map(aggregate -> aggregate.getLastWorkSheet().getFactFuelBalance())
                .reduce(0.0, Double::sum));


        sumWorkSheetValues.add(aggregates.stream()
                .map(aggregate -> aggregate.getLastWorkSheet().getFactOilBalance())
                .reduce(0.0, Double::sum));

        sumWorkSheetValues.add(aggregates.stream()
                .map(aggregate -> aggregate.getEngine().getFromBeginningWork())
                .reduce(0.0, Double::sum));

        return sumWorkSheetValues;
    }
}
