package com.simplon.labxpert.service.impl;

import com.simplon.labxpert.exception.handler.CustomNotFoundException;
import com.simplon.labxpert.mapper.PatientMapper;
import com.simplon.labxpert.model.dto.PatientDTO;
import com.simplon.labxpert.model.entity.Patient;
import com.simplon.labxpert.repository.PatientRepository;
import com.simplon.labxpert.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation of the Patient service.
 * It contains the methods that the service will implement.
 */
@Service
public class PatientServiceImpl implements PatientService {
    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;

    @Autowired
    public PatientServiceImpl(PatientRepository patientRepository, PatientMapper patientMapper) {
        this.patientRepository = patientRepository;
        this.patientMapper = patientMapper;
    }

    @Override
    public PatientDTO getPatientById(long patientId) {
        Optional<Patient> optionalPatient = patientRepository.findById(patientId);
        if (optionalPatient.isPresent()) {
            Patient patient = optionalPatient.get();
            return patientMapper.toDTO(patient);
        } else {
            throw new NoSuchElementException("Patient not found with ID: " + patientId);
        }
    }

    @Override
    public List<PatientDTO> getAllPatients() {
        List<Patient> patients = patientRepository.findAll();
        return patients.stream().map(patientMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public PatientDTO createPatient(PatientDTO patientDTO) {
        Patient patient = patientMapper.toEntity(patientDTO);
        Optional<Patient> optionalPatient = patientRepository.findByPatientEmail(patient.getPatientEmail());
        if (optionalPatient.isPresent()) {
            throw new DataIntegrityViolationException("patient with that email" + patient.getPatientEmail() + "already exist ");
        }
        Patient savedPatient = patientRepository.save(patient);
        return patientMapper.toDTO(savedPatient);
    }

    @Override
    public PatientDTO updatePatient(long patientId, PatientDTO patientDTO) {
        Patient patient = patientMapper.toEntity(patientDTO);
        Optional<Patient> existingPatient = patientRepository.findById(patientId);
        if (!existingPatient.isPresent()) {
            throw new CustomNotFoundException("Patient not found with ID: " + patientId, HttpStatus.NOT_FOUND);
        }
        Optional<Patient> existingPatientEmail = patientRepository.findByPatientEmailAndPatientIDNot(patient.getPatientEmail(), patientId);
        if (existingPatientEmail.isPresent()) {
            throw new CustomNotFoundException("patient with that email" + patient.getPatientEmail() + "already exist ", HttpStatus.BAD_REQUEST);
        }
        existingPatient.get().setPatientID(patientId);
        existingPatient.get().setFirstName(patient.getFirstName());
        existingPatient.get().setLastName(patient.getLastName());
        existingPatient.get().setPatientEmail(patient.getPatientEmail());
        existingPatient.get().setPhoneNumber(patient.getPhoneNumber());
        existingPatient.get().setAddress(patient.getAddress());
        existingPatient.get().setDateOfBirth(patient.getDateOfBirth());
        existingPatient.get().setGender(patient.getGender());
        patientRepository.save(existingPatient.get());
        return patientMapper.toDTO(existingPatient.get());
    }

    @Override
    public void deletePatient(long patientId) {
        Optional<Patient> optionalPatient = patientRepository.findById(patientId);
        if (optionalPatient.isPresent()) {
            patientRepository.deleteById(patientId);
        } else {
            throw new CustomNotFoundException("Patient not found with ID: " + patientId, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public PatientDTO getPatientByEmail(String email) {
        Optional<Patient> patient = patientRepository.findByPatientEmail(email);
        if (patient.isPresent()) {
            Patient existingPatient = patient.get();
            return patientMapper.toDTO(existingPatient);
        } else {
            throw new CustomNotFoundException("Patient with that email is not found" + email, HttpStatus.NOT_FOUND);
        }
    }
}
