package com.simplon.labxpert.repository;

import com.simplon.labxpert.model.entity.Sample;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 * Repository for the Sample entity.
 * It contains all the methods that we need to interact with the Sample table in the database.
 */
@Repository
public interface SampleRepository extends JpaRepository<Sample, Long> {
}
