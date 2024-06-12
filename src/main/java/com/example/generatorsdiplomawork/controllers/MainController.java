package com.example.generatorsdiplomawork.controllers;

import com.example.generatorsdiplomawork.entities.Aggregate;
import com.example.generatorsdiplomawork.repositories.AggregateRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
public class MainController {
    private List<Aggregate> aggregates;
    private final AggregateRepository aggregateRepository;

    public MainController(AggregateRepository aggregateRepository) {
        this.aggregateRepository = aggregateRepository;
        aggregates = aggregateRepository.findAll();
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("aggregates", aggregates);
        return "index";
    }

    @PostMapping("search/aggregate/name")
    public String searchAggregateByName(@RequestParam String searchedName, Model model) {
        List<Aggregate> searchedAggregate = aggregates.stream()
                .filter(x -> x.getName().toLowerCase().contains(searchedName.toLowerCase())).toList();
        model.addAttribute("aggregates", searchedAggregate);
        return "index";
    }
}