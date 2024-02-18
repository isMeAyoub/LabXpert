package com.simplon.labxpert.repository;

import com.simplon.labxpert.model.entity.Reagent;
import com.simplon.labxpert.model.enums.ReagentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
/**
 * Repository for the Reagent entity.
 * It contains all the methods that we need to interact with the Reagent table in the database.
 */
@Repository
public interface ReagentRepository extends JpaRepository<Reagent, Long> {
    List<Reagent> findAllByReagentStatus(ReagentStatus status);
    Optional<Reagent> findByReagentNameIgnoreCase(String reagentName);
    Optional<Reagent> findByReagentNameAndReagentIDNot(String reagentName, Long reagentID);

}
