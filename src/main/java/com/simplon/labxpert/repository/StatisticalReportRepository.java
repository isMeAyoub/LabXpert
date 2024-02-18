package com.simplon.labxpert.repository;

import com.simplon.labxpert.model.entity.StatisticalReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 * Repository for the StatisticalReport entity.
 * It contains all the methods that we need to interact with the StatisticalReport table in the database.
 */
@Repository
public interface StatisticalReportRepository extends JpaRepository<StatisticalReport, Long> {
}
