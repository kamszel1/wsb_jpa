package com.jpacourse.persistance.dao.impl;

import com.jpacourse.persistance.dao.PatientDao;
import com.jpacourse.persistance.entity.PatientEntity;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class PatientDaoImpl extends AbstractDao<PatientEntity, Long> implements PatientDao {

    @Override
    public List<PatientEntity> findBySurname(String surname) {
        return entityManager.createQuery("SELECT p FROM PatientEntity p WHERE p.lastName = :surname", PatientEntity.class)
                .setParameter("surname", surname)
                .getResultList();
    }

    @Override
    public List<PatientEntity> findPatientsWithMoreVisitsThan(int visits) {
        return entityManager.createQuery("SELECT p FROM PatientEntity p WHERE p.visits.size > :visits", PatientEntity.class)
                .setParameter("visits", visits)
                .getResultList();
    }

    @Override
    public List<PatientEntity> findPatientsBornBefore(LocalDate date) {
        return entityManager.createQuery("SELECT p FROM PatientEntity p WHERE p.dateOfBirth < :date", PatientEntity.class)
                .setParameter("date", date)
                .getResultList();
    }
}
