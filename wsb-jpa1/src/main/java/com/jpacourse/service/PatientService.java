package com.jpacourse.service;

import com.jpacourse.dto.PatientTO;
import com.jpacourse.dto.VisitTO;
import com.jpacourse.persistance.entity.MedicalTreatmentEntity;
import com.jpacourse.persistance.entity.PatientEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface PatientService {

    PatientTO findById(final Long id);

    void deleteById(final Long id);

    PatientTO createVisit(Long patientId, Long doctorId, LocalDateTime visitTime, List<MedicalTreatmentEntity> treatments, String visitDescription);

    List<VisitTO> findVisitsByPatientId(Long patientId);

    List<PatientTO> findPatientsByLastName(String lastName);

    List<PatientTO> findPatientsWithMoreVisitsThan(int visitCount);

    List<PatientTO> findPatientsBornBefore(LocalDate date);


}
