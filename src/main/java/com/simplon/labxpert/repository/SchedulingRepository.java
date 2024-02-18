package com.simplon.labxpert.repository;

import com.simplon.labxpert.model.entity.Scheduling;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 * Repository for the Scheduling entity.
 * It contains all the methods that we need to interact with the Scheduling table in the database.
 */
@Repository
public interface SchedulingRepository extends JpaRepository<Scheduling, Long> {
}
