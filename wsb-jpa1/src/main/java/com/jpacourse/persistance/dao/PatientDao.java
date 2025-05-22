package com.jpacourse.persistance.dao;

import com.jpacourse.persistance.entity.PatientEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface PatientDao extends Dao<PatientEntity, Long> {

    List<PatientEntity> findBySurname(String surname);

    List<PatientEntity> findPatientsWithMoreVisitsThan(int visits);

    List<PatientEntity> findPatientsBornBefore(LocalDate date);

    PatientEntity addVisitToPatient(Long patientId, Long doctorId, LocalDateTime visitDate, String description);

    List<PatientEntity> findByRegistrationDateAfter(LocalDate date);
}
