package com.simplon.labxpert.service;

import com.simplon.labxpert.model.dto.SchedulingDTO;

import java.util.List;

/**
 * Interface for the Scheduling service.
 * It contains the methods that the service will implement.
 */
public interface SchedulingService {
    SchedulingDTO getSchedulingById(long schedulingId);
    List<SchedulingDTO> getAllSchedulings();
    SchedulingDTO createScheduling(SchedulingDTO schedulingDTO);
    SchedulingDTO updateScheduling(long schedulingId, SchedulingDTO schedulingDTO);
    void deleteScheduling(long schedulingId);
}
