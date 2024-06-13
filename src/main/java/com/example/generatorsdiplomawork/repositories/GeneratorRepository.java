package com.example.generatorsdiplomawork.repositories;

import com.example.generatorsdiplomawork.entities.Generator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeneratorRepository extends JpaRepository<Generator, Long> {
}
