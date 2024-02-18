package com.simplon.labxpert.repository;

import com.simplon.labxpert.model.entity.AnalysisReagent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 * Repository for the AnalysisReagent entity.
 * It contains all the methods that we need to interact with the AnalysisReagent table in the database.
 */
@Repository
public interface AnalysisReagentRepository extends JpaRepository<AnalysisReagent, Long>{
}
