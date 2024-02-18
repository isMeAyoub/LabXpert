package com.simplon.labxpert.repository;

import com.simplon.labxpert.model.entity.Analysis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 * Repository for the Analysis entity.
 * It contains all the methods that we need to interact with the Analysis table in the database.
 */
@Repository
public interface AnalysisRepository extends JpaRepository<Analysis, Long> {
}
