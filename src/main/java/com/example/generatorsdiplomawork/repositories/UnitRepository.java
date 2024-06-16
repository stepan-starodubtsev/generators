package com.example.generatorsdiplomawork.repositories;

import com.example.generatorsdiplomawork.entities.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnitRepository extends JpaRepository<Unit, Long> {
    public Unit findByName(String name);
}
