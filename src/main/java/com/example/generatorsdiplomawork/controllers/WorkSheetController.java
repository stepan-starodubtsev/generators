package com.example.generatorsdiplomawork.controllers;

import com.example.generatorsdiplomawork.entities.Aggregate;
import com.example.generatorsdiplomawork.entities.FuelType;
import com.example.generatorsdiplomawork.entities.Month;
import com.example.generatorsdiplomawork.entities.WorkSheet;
import com.example.generatorsdiplomawork.repositories.AggregateRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Stack;

@Controller
@RequestMapping("/aggregates/workSheets")
public class WorkSheetController {
    private final AggregateRepository aggregateRepository;
    public WorkSheetController(AggregateRepository aggregateRepository) {
        this.aggregateRepository = aggregateRepository;
    }

    @GetMapping("/{aggregateId}")
    public String workSheetsPage(@PathVariable Long aggregateId, Model model){
        Aggregate aggregate = aggregateRepository.findById(aggregateId).get();

        model.addAttribute("aggregate", aggregate);
        model.addAttribute("workSheets", aggregate.getWorkSheets());
        return "workSheetsPage";
    }

    @PostMapping("/search/workSheet/month/{aggregateId}")
    public String searchAggregateByName(@PathVariable Long aggregateId,
                                        @RequestParam String workSheetMonth, Model model) {
        Aggregate aggregate = aggregateRepository.findById(aggregateId).get();
        List<WorkSheet> workSheets = aggregate.getWorkSheets();
        if (!workSheets.isEmpty()){
            List<WorkSheet> searshedList = workSheets.stream()
                    .filter(x -> x.getCreatingDate().getMonthValue() ==
                            Month.valueOf(workSheetMonth.toUpperCase()).ordinal()).toList();
            model.addAttribute("workSheets", searshedList);
        }
        return "index";
    }

    @PostMapping("/aggregates/workSheets/create/{aggregateId}")
    public String create(@PathVariable Long aggregateId,
                         @RequestParam String fuelEarned,
                         @RequestParam String oilEarned){
        Aggregate aggregate = aggregateRepository.findById(aggregateId).get();
        double startFuelBalance = 0;
        double startOilBalance = 0;
        if (!aggregate.getWorkSheets().isEmpty()){
            WorkSheet lastWorkSheet = aggregate.getLastWorkSheet();
            startFuelBalance =+ lastWorkSheet.getEndFuelBalance();
            startOilBalance =+ lastWorkSheet.getStartOilBalance();
        }
        double fromBeginningMonthWork = 0;
        double usedFuel = 0;
        double usedOil = 0;

        WorkSheet workSheet = new WorkSheet(fromBeginningMonthWork, startFuelBalance, startOilBalance,
                Double.parseDouble(fuelEarned), Double.parseDouble(oilEarned), usedFuel, usedOil, new Stack<>());

        aggregate.getWorkSheets().add(workSheet);
        aggregateRepository.save(aggregate);
        return "redirect: workSheetsPage";
    }

}
