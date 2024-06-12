package com.example.generatorsdiplomawork.repositories;

import com.example.generatorsdiplomawork.entities.ElectricCurrentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ElectricCurrentTypeRepository extends JpaRepository<ElectricCurrentType, Long> {
}
