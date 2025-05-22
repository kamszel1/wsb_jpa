package com.jpacourse.persistance.dao.impl;

import com.jpacourse.persistance.dao.PatientDao;
import com.jpacourse.persistance.entity.DoctorEntity;
import com.jpacourse.persistance.entity.PatientEntity;
import com.jpacourse.persistance.entity.VisitEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PatientDaoImpl extends AbstractDao<PatientEntity, Long> implements PatientDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<PatientEntity> findBySurname(String surname) {
        return entityManager.createQuery("SELECT p FROM PatientEntity p WHERE p.lastName = :surname", PatientEntity.class)
                .setParameter("surname", surname)
                .getResultList();
    }

    @Override
    public List<PatientEntity> findPatientsWithMoreVisitsThan(int visits) {
        String s = "SELECT p FROM PatientEntity p WHERE SIZE(p.visits) > :visitCount";
        TypedQuery<PatientEntity> query = entityManager.createQuery(s, PatientEntity.class);
        query.setParameter("visitCount", visits);
        return query.getResultList();
    }
    @Override
    public List<PatientEntity> findPatientsBornBefore(LocalDate date) {
        String s = "SELECT p FROM PatientEntity p WHERE p.dateOfBirth < :date";
        TypedQuery<PatientEntity> query = entityManager.createQuery(s, PatientEntity.class);
        query.setParameter("date", date);
        return query.getResultList();
    }

    @Override
    public PatientEntity addVisitToPatient(Long patientId, Long doctorId, LocalDateTime visitDate, String description) {
        PatientEntity patient = entityManager.find(PatientEntity.class, patientId);

        if(patient == null){
            throw new RuntimeException("Patient not found");
        }
        DoctorEntity doctor = entityManager.find(DoctorEntity.class, doctorId);

        if(doctor == null){
            throw new RuntimeException("Doctor not found");
        }
        VisitEntity visit = new VisitEntity();
        visit.setDoctor(doctor);
        visit.setTreatments(new ArrayList<>());
        visit.setPatient(patient);
        visit.setTime(visitDate);
        visit.setDescription(description);
        patient.getVisits().add(visit);
        return entityManager.merge(patient);
    }

    public List<PatientEntity> findByRegistrationDateAfter(LocalDate date) {
        TypedQuery<PatientEntity> query = entityManager.createQuery("SELECT p FROM PatientEntity p WHERE p.registrationDate > :date", PatientEntity.class);
        query.setParameter("date", date);
        return query.getResultList();
    }


}
