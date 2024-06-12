package com.example.generatorsdiplomawork.repositories;

import com.example.generatorsdiplomawork.entities.ChassisType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChassisTypeRepository extends JpaRepository<ChassisType, Long> {
}
