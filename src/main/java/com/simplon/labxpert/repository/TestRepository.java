package com.simplon.labxpert.repository;

import com.simplon.labxpert.model.entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 * Repository for the Test entity.
 * It contains all the methods that we need to interact with the Test table in the database.
 */
@Repository
public interface TestRepository extends JpaRepository<Test, Long> {
}
