package com.simplon.labxpert.repository;

import com.simplon.labxpert.model.entity.TestResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 * Repository for the TestResult entity.
 * It contains all the methods that we need to interact with the TestResult table in the database.
 */
@Repository
public interface TestResultRepository extends JpaRepository<TestResult, Long> {
}
