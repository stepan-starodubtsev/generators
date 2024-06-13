package com.example.generatorsdiplomawork.repositories;

import com.example.generatorsdiplomawork.entities.Chassis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChassisRepository extends JpaRepository<Chassis, Long> {
}
