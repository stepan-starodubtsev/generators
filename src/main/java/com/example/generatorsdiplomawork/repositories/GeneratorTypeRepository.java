package com.example.generatorsdiplomawork.repositories;

import com.example.generatorsdiplomawork.entities.GeneratorType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeneratorTypeRepository extends JpaRepository<GeneratorType, Long> {
}
