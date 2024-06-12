package com.example.generatorsdiplomawork.repositories;

import com.example.generatorsdiplomawork.entities.Aggregate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AggregateRepository extends JpaRepository<Aggregate, Long> {
    public Aggregate findByName(String aggregateName);
}
