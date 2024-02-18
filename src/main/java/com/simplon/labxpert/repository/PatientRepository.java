package com.simplon.labxpert.repository;

import com.simplon.labxpert.model.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
/**
 * Repository for the Patient entity.
 * It contains all the methods that we need to interact with the Patient table in the database.
 */
@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    Optional<Patient> findByPatientEmail(String email);
    Optional<Patient> findByPatientEmailAndPatientIDNot(String email , Long patientId);
}
