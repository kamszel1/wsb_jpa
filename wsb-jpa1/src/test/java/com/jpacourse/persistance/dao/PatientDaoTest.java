package com.jpacourse.persistance.dao;

import com.jpacourse.persistance.entity.PatientEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class PatientDaoTest {
    
    @Autowired
    private PatientDao patientDao;

    @Test
    public void testFindPatientsByLastName() {
        String lastName = "Kowalski";

        List<PatientEntity> patients = patientDao.findBySurname(lastName);

        assertThat(patients).isNotEmpty();
        assertEquals(1, patients.size());
        assertEquals("Jan", patients.get(0).getFirstName());
        assertEquals("Kowalski", patients.get(0).getLastName());
    }

    @Test
    public void testFindPatientsWithMoreVisitsThan() {
        int visitCount = 1;
        List<PatientEntity> patients = patientDao.findPatientsWithMoreVisitsThan(visitCount);

        assertThat(patients).isNotEmpty();
        assertEquals(1, patients.size());
        assertEquals("Piotr", patients.get(0).getFirstName());
        assertEquals("Nowak", patients.get(0).getLastName());
        assertTrue(patients.get(0).getVisits().size() > visitCount);
    }

    @Test
    public void testFindPatientsBornBefore() {
        LocalDate date = LocalDate.of(1980, 1, 1);

        List<PatientEntity> patients = patientDao.findPatientsBornBefore(date);

        assertThat(patients).isNotEmpty();
        assertEquals(1, patients.size());
        assertEquals("Jan", patients.get(0).getFirstName());
        assertEquals("Kowalski", patients.get(0).getLastName());
    }

    @Test
    public void testFindByRegistrationDateAfter() {
        LocalDate date = LocalDate.of(2025, 1, 1);
        List<PatientEntity> patients = patientDao.findByRegistrationDateAfter(date);

        assertThat(patients).isNotEmpty();
        assertEquals(1, patients.size());
        assertEquals("Piotr", patients.get(0).getFirstName());
        assertEquals("Nowak", patients.get(0).getLastName());

    }
    
}
