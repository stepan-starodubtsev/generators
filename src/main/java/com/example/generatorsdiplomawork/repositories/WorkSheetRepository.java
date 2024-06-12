package com.example.generatorsdiplomawork.repositories;

import com.example.generatorsdiplomawork.entities.WorkSheet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkSheetRepository extends JpaRepository<WorkSheet, Long> {
}
