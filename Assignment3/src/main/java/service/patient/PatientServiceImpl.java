package service.patient;

import model.Patient;
import model.validation.Notification;

import java.util.List;

public class PatientServiceImpl implements PatientService {
    @Override
    public Notification<Boolean> addPatient(Patient p) {
        return null;
    }

    @Override
    public Notification<Boolean> updatePatient(Patient p) {
        return null;
    }

    @Override
    public void deletePatient(Long id) {

    }

    @Override
    public List<Patient> findAllPatients() {
        return null;
    }
}
