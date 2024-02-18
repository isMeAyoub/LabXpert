package com.simplon.labxpert.service.impl;

import com.simplon.labxpert.exception.handler.CustomNotFoundException;
import com.simplon.labxpert.mapper.GlobalMapper;
import com.simplon.labxpert.mapper.SampleMapper;
import com.simplon.labxpert.model.dto.PatientDTO;
import com.simplon.labxpert.model.dto.SampleDTO;
import com.simplon.labxpert.model.entity.Patient;
import com.simplon.labxpert.model.entity.Sample;
import com.simplon.labxpert.model.enums.SampleStatus;
import com.simplon.labxpert.repository.PatientRepository;
import com.simplon.labxpert.repository.SampleRepository;
import com.simplon.labxpert.service.SampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 * Implementation of the Sample service.
 * It contains the methods that the service will implement.
 */
@Service
public class SampleServiceImpl implements SampleService {

    private final SampleMapper sampleMapper;
    private final SampleRepository sampleRepository;
    private final PatientRepository patientRepository;

    @Autowired
    private SampleServiceImpl(SampleMapper sampleMapper, SampleRepository sampleRepository, PatientRepository patientRepository) {
        this.sampleMapper = sampleMapper;
        this.sampleRepository = sampleRepository;
        this.patientRepository = patientRepository;
    }

    @Override
    public List<SampleDTO> getAllSimple() {
        List<Sample> samples = sampleRepository.findAll();
        return samples.stream().map(sampleMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public SampleDTO createSample(SampleDTO sampleDTO) {
        Sample sample = sampleMapper.toEntity(sampleDTO);
        Optional<Patient> optionalPatient = patientRepository.findById(sampleDTO.getPatientDTO().getPatientID());
        if (!optionalPatient.isPresent()) {
            throw new CustomNotFoundException("Patient not found", HttpStatus.BAD_REQUEST);
        }
        sample.setPatient(optionalPatient.get());
        sample.setSampleStatus(SampleStatus.PENDING);
        Sample savedSample = sampleRepository.save(sample);
        SampleDTO savedSampleDTO = sampleMapper.toDTO(savedSample);
        return savedSampleDTO;
    }

    @Override
    public SampleDTO getSampleById(long sampleId) {
        Optional<Sample> optionalSample = sampleRepository.findById(sampleId);
        if (optionalSample.isPresent()) {
            Sample sample = optionalSample.get();
            return sampleMapper.toDTO(sample);
        } else {
            throw new CustomNotFoundException("Sample not found", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void deleteSample(long sampleId) {
        Optional<Sample> optionalSample = sampleRepository.findById(sampleId);
        if (!optionalSample.isPresent()) {
            throw new CustomNotFoundException("Sample not found with ID: " + sampleId, HttpStatus.NOT_FOUND);
        }
        sampleRepository.deleteById(sampleId);
    }
}
