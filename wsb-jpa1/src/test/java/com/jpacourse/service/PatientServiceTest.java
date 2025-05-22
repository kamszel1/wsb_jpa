package com.jpacourse.service;

import com.jpacourse.dto.PatientTO;
import com.jpacourse.dto.VisitTO;
import com.jpacourse.persistance.dao.PatientDao;
import com.jpacourse.persistance.entity.MedicalTreatmentEntity;
import com.jpacourse.persistance.entity.PatientEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class PatientServiceTest {

    @Autowired
    private PatientService patientService;

    @Autowired
    private PatientDao patientDao;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void testFindById() {
        Long patientId = 1L;

        PatientTO patientTO = patientService.findById(patientId);

        assertThat(patientTO).isNotNull();
        assertThat(patientTO.getId()).isEqualTo(patientId);
        assertThat(patientTO.getFirstName()).isEqualTo("Piotr");
        assertThat(patientTO.getLastName()).isEqualTo("Nowak");

        assertThat(patientTO.getRegistrationDate()).isEqualTo(LocalDate.of(2025, 02, 15));

        assertThat(patientTO.getVisits()).isNotEmpty();
        assertThat(patientTO.getVisits().size()).isEqualTo(2);
    }

    @Test
    @Transactional
    public void testCreateVisit() {
        Long patientId = 2L;
        Long doctorId = 2L;
        LocalDateTime visitTime = LocalDateTime.now();
        String visitDescription = "Test visit";
        List<MedicalTreatmentEntity> treatments = new ArrayList<>();

        PatientEntity patientBefore = entityManager.find(PatientEntity.class, patientId);
        int visitCountBefore = patientBefore.getVisits().size();

        PatientTO updatedPatient = patientService.createVisit(patientId, doctorId, visitTime,treatments, visitDescription);

        assertThat(updatedPatient).isNotNull();
        assertThat(updatedPatient.getVisits().size()).isEqualTo(visitCountBefore + 1);

        VisitTO newVisit = updatedPatient.getVisits().stream()
                .filter(v -> visitDescription.equals(v.getDescription()))
                .findFirst()
                .orElse(null);

        assertThat(newVisit).isNotNull();
        assertThat(newVisit.getTime()).isEqualTo(visitTime);
        assertThat(newVisit.getDoctorFirstName()).isEqualTo("Michal");
        assertThat(newVisit.getDoctorLastName()).isEqualTo("Test");
    }

    @Test
    public void testFindVisitsByPatientId() {
        Long patientId = 1L;

        List<VisitTO> visits = patientService.findVisitsByPatientId(patientId);

        assertThat(visits).isNotEmpty();
        assertEquals(2, visits.size());
        assertTrue(visits.stream().allMatch(v -> v.getDescription() != null));
        assertTrue(visits.stream().allMatch(v -> v.getTime() != null));
        assertTrue(visits.stream().allMatch(v -> v.getDoctorFirstName() != null));
        assertTrue(visits.stream().allMatch(v -> v.getDoctorLastName() != null));
    }


}
