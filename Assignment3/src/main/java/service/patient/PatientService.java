package service.patient;

import model.Patient;
import model.validation.Notification;

import java.util.List;

public interface PatientService {

    Notification<Boolean> addPatient(Patient p);
    Notification<Boolean> updatePatient(Patient p);
    void deletePatient(Long id);
    List<Patient> findAllPatients();

}
