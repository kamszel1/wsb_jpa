package com.jpacourse.service.impl;

import com.jpacourse.dto.PatientTO;
import com.jpacourse.dto.VisitTO;
import com.jpacourse.mapper.PatientMapper;
import com.jpacourse.persistance.dao.DoctorDao;
import com.jpacourse.persistance.dao.PatientDao;
import com.jpacourse.persistance.dao.VisitDao;
import com.jpacourse.persistance.entity.DoctorEntity;
import com.jpacourse.persistance.entity.MedicalTreatmentEntity;
import com.jpacourse.persistance.entity.PatientEntity;
import com.jpacourse.persistance.entity.VisitEntity;
import com.jpacourse.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PatientServiceImpl implements PatientService
{
    private final PatientDao patientDao;
    private final DoctorDao doctorDao;
    private final VisitDao visitDao;

    @Autowired
    public PatientServiceImpl(PatientDao patientDao, VisitDao visitDao, DoctorDao doctorDao) {
        this.patientDao = patientDao;
        this.doctorDao = doctorDao;
        this.visitDao = visitDao;
    }

    @Override
    public PatientTO findById(Long id) {
        final PatientEntity entity = patientDao.findOne(id);
        return PatientMapper.mapToTO(entity);
    }

    @Override
    public void deleteById(Long id) {
        patientDao.delete(id);
    }

    @Override
    public PatientTO createVisit(Long patientId, Long doctorId, LocalDateTime visitTime, List<MedicalTreatmentEntity> treatments, String visitDescription) {
        PatientEntity patient = patientDao.findOne(patientId);
        if (patient == null) {
            throw new RuntimeException("Patient not found");
        }
        DoctorEntity doctor = this.doctorDao.findOne(doctorId);
        if (doctor == null ) {
            throw new RuntimeException("Doctor not found");
        }

        VisitEntity visit = new VisitEntity();
        visit.setPatient(patient);
        visit.setDoctor(doctor);
        visit.setTreatments(treatments);
        visit.setTime(visitTime);
        visit.setDescription(visitDescription);

        patient.getVisits().add(visit);
        PatientEntity updatedPatient = patientDao.update(patient);

        return PatientMapper.mapToTO(updatedPatient);
    }

    @Override
    public List<VisitTO> findVisitsByPatientId(Long patientId) {
        PatientEntity patient = patientDao.findOne(patientId);
        if (patient == null) {
            throw new RuntimeException("Patient not found");
        }
        List<VisitEntity> visits = patient.getVisits();
        return visits.stream()
                .map(visit -> {
                    VisitTO visitTO = new VisitTO();
                    visitTO.setId(visit.getId());
                    visitTO.setDescription(visit.getDescription());
                    visitTO.setTime(visit.getTime());
                    visitTO.setDoctorFirstName(visit.getDoctor().getFirstName());
                    visitTO.setDoctorLastName(visit.getDoctor().getLastName());
                    return visitTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<PatientTO> findPatientsByLastName(String lastName) {
        List<PatientEntity> patients = patientDao.findBySurname(lastName);
        return patients.stream()
                .map(PatientMapper::mapToTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PatientTO> findPatientsWithMoreVisitsThan(int visitCount) {
        List<PatientEntity> patients = patientDao.findPatientsWithMoreVisitsThan(visitCount);
        return patients.stream()
                .map(PatientMapper::mapToTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PatientTO> findPatientsBornBefore(LocalDate date) {
        List<PatientEntity> patients = patientDao.findPatientsBornBefore(date);
        return patients.stream()
                .map(PatientMapper::mapToTO)
                .collect(Collectors.toList());
    }
}
