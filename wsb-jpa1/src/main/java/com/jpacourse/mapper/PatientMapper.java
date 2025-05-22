package com.jpacourse.mapper;

import com.jpacourse.dto.PatientTO;
import com.jpacourse.dto.VisitTO;
import com.jpacourse.persistance.entity.PatientEntity;
import com.jpacourse.persistance.entity.VisitEntity;
import com.jpacourse.persistance.entity.MedicalTreatmentEntity;

import java.util.stream.Collectors;

public final class PatientMapper
{
    public static PatientTO mapToTO(final PatientEntity patientEntity) {
        if (patientEntity == null){
            return null;
        }
        final PatientTO patientTO = new PatientTO();
        patientTO.setId(patientEntity.getId());
        patientTO.setFirstName(patientEntity.getFirstName());
        patientTO.setLastName(patientEntity.getLastName());
        patientTO.setTelephoneNumber(patientEntity.getTelephoneNumber());
        patientTO.setEmail(patientEntity.getEmail());
        patientTO.setPatientNumber(patientEntity.getPatientNumber());
        patientTO.setDateOfBirth(patientEntity.getDateOfBirth());
        patientTO.setRegistrationDate(patientEntity.getRegistrationDate());
        
        if (patientEntity.getVisits() != null) {
            patientTO.setVisits(patientEntity.getVisits().stream()
                .map(PatientMapper::mapVisitToTO)
                .collect(Collectors.toList()));
        }
        
        return patientTO;
    }

    public static PatientEntity mapToEntity(final PatientTO patientTO) {
        if(patientTO == null){
            return null;
        }
        PatientEntity patientEntity = new PatientEntity();
        patientEntity.setId(patientTO.getId());
        patientEntity.setFirstName(patientTO.getFirstName());
        patientEntity.setLastName(patientTO.getLastName());
        patientEntity.setTelephoneNumber(patientTO.getTelephoneNumber());
        patientEntity.setEmail(patientTO.getEmail());
        patientEntity.setPatientNumber(patientTO.getPatientNumber());
        patientEntity.setDateOfBirth(patientTO.getDateOfBirth());
        patientEntity.setRegistrationDate(patientTO.getRegistrationDate());

        return patientEntity;
    }
    
    private static VisitTO mapVisitToTO(final VisitEntity visitEntity) {
        if (visitEntity == null) {
            return null;
        }
        
        final VisitTO visitTO = new VisitTO();
        visitTO.setId(visitEntity.getId());
        visitTO.setDescription(visitEntity.getDescription());
        visitTO.setTime(visitEntity.getTime());
        
        if (visitEntity.getDoctor() != null) {
            visitTO.setDoctorFirstName(visitEntity.getDoctor().getFirstName());
            visitTO.setDoctorLastName(visitEntity.getDoctor().getLastName());
        }
        
        if (visitEntity.getTreatments() != null) {
            visitTO.setTreatmentTypes(visitEntity.getTreatments().stream()
                .map(MedicalTreatmentEntity::getType)
                .map(type -> type != null ? type.name() : null)
                .collect(Collectors.toList()));
        }
        
        return visitTO;
    }
}