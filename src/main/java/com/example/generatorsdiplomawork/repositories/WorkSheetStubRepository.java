package com.example.generatorsdiplomawork.repositories;

import com.example.generatorsdiplomawork.entities.WorkSheetStub;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkSheetStubRepository extends JpaRepository<WorkSheetStub, Long> {
}
