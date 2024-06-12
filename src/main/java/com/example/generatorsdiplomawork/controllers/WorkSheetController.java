package com.example.generatorsdiplomawork.controllers;

import com.example.generatorsdiplomawork.entities.Aggregate;
import com.example.generatorsdiplomawork.entities.Month;
import com.example.generatorsdiplomawork.entities.WorkSheet;
import com.example.generatorsdiplomawork.entities.WorkSheetStub;
import com.example.generatorsdiplomawork.repositories.AggregateRepository;
import com.example.generatorsdiplomawork.repositories.WorkSheetRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Stack;

@Controller
@RequestMapping("/aggregates/{aggregateId}/workSheets")
public class WorkSheetController {
    private final AggregateRepository aggregateRepository;
    private final WorkSheetRepository workSheetRepository;

    public WorkSheetController(AggregateRepository aggregateRepository, WorkSheetRepository workSheetRepository) {
        this.aggregateRepository = aggregateRepository;
        this.workSheetRepository = workSheetRepository;
    }

    @GetMapping("/")
    public String workSheetsPage(@PathVariable Long aggregateId, Model model){
        Aggregate aggregate = aggregateRepository.findById(aggregateId).get();

        model.addAttribute("aggregate", aggregate);
        model.addAttribute("workSheets", aggregate.getWorkSheets());
        return "workSheetsPage";
    }

    @PostMapping("/search/workSheet/month")
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

    @PostMapping("/create")
    public String create(@PathVariable Long aggregateId,
                         @RequestParam String fuelSaved,
                         @RequestParam String oilSaved){
        Aggregate aggregate = aggregateRepository.findById(aggregateId).get();

        WorkSheet workSheet = new WorkSheet(
                Double.parseDouble(fuelSaved), Double.parseDouble(oilSaved), new Stack<>());

        aggregate.getWorkSheets().add(workSheet);
        aggregateRepository.save(aggregate);
        return "workSheetsPage";
    }

    @GetMapping("/{workSheetId}")
    public String getWorkSheetPage(@PathVariable Long aggregateId,
                                   @PathVariable Long workSheetId,
                                   Model model){
        Aggregate aggregate = aggregateRepository.findById(aggregateId).get();
        WorkSheet workSheet = aggregate.getWorkSheets().stream()
                .filter(x -> x.getId().equals(workSheetId)).toList().get(0);

        model.addAttribute("aggregate", aggregate);
        model.addAttribute("workSheet", workSheet);
        model.addAttribute("normalRateFuel", workSheet.getUsedFuel());
        model.addAttribute("normalRateOil", workSheet.getUsedOil());
        return "workSheetPage";
    }

    @GetMapping("/{workSheetId}/stub")
    public String getWorkSheetStubPage(@PathVariable Long aggregateId,
                                   @PathVariable Long workSheetId,
                                   Model model){
        Aggregate aggregate = aggregateRepository.findById(aggregateId).get();
        WorkSheet workSheet = aggregate.getWorkSheets().stream()
                .filter(x -> x.getId().equals(workSheetId)).toList().get(0);

        model.addAttribute("aggregate", aggregate);
        model.addAttribute("workSheet", workSheet);
        return "workSheetStubPage";
    }

    @PostMapping("/{workSheetId}/stub/create")
    public String createStub(@PathVariable Long aggregateId,
                             @PathVariable Long workSheetId,
                             @RequestParam String startDate,
                             @RequestParam String endDate,
                             @RequestParam String obtainedFuel,
                             @RequestParam String obtainedOil,
                             @RequestParam String workText){
        Aggregate aggregate = aggregateRepository.findById(aggregateId).get();
        WorkSheet workSheet = aggregate.getWorkSheets().stream()
                .filter(x -> x.getId().equals(workSheetId)).toList().get(0);

        Double fuelRate = aggregate.getEngine().getEngineType().getFuelRate();
        Double oilRate = aggregate.getEngine().getEngineType().getOilRate();
        double obtainedFuelParsed = Double.parseDouble(obtainedFuel);
        double obtainedOilParsed = Double.parseDouble(obtainedOil);

        WorkSheetStub workSheetStub = new WorkSheetStub(LocalDateTime.parse(startDate), LocalDateTime.parse(endDate),
                workText, obtainedFuelParsed, obtainedOilParsed, fuelRate, oilRate);

        workSheet.obtainFuel(obtainedFuelParsed);
        workSheet.obtainOil(obtainedOilParsed);
        workSheet.useFuel(workSheetStub.getUsedFuel());
        workSheet.useOil(workSheetStub.getUsedOil());

        workSheet.getWorkSheetStubs().add(workSheetStub);
        workSheetRepository.save(workSheet);
        return  "workSheetStubPage";
    }
}
