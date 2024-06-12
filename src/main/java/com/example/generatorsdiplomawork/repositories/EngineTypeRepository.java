package com.example.generatorsdiplomawork.repositories;

import com.example.generatorsdiplomawork.entities.EngineType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EngineTypeRepository extends JpaRepository<EngineType, Long> {
}
