package service.patient;

import dto.PatientDTO;
import model.Patient;
import model.validation.Notification;

import java.util.Date;
import java.util.List;

public interface PatientService {

    Notification<Boolean> addPatient(String name,Long idCardNr,Long cnp,String address,Date dateOfBirth);
    Notification<Boolean> updatePatient(Long id,String name,Long idCardNr,Long cnp,String address);
    void deletePatient(Long id);
    List<Patient> findAllPatients();

}
