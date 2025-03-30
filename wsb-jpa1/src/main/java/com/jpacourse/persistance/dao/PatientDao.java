package com.jpacourse.persistance.dao;

import com.jpacourse.persistance.entity.PatientEntity;

import java.time.LocalDate;
import java.util.List;

public interface PatientDao extends Dao<PatientEntity, Long> {

    List<PatientEntity> findBySurname(String surname);

    List<PatientEntity> findPatientsWithMoreVisitsThan(int visits);

    List<PatientEntity> findPatientsBornBefore(LocalDate date);

}
