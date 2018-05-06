package service.patient;

import dto.PatientDTO;
import model.Patient;
import model.builder.PatientBuilder;
import model.validation.Notification;
import model.validation.PatientValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.patient.PatientRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientRepository patientRepository;


    @Override
    public Notification<Boolean> addPatient(String name,Long idCardNr,Long cnp,String address,Date dateOfBirth) {
        Patient patient=new PatientBuilder().setName(name).setCNP(cnp).setIdCardNr(idCardNr).
                setDateOfBirth(dateOfBirth).setAddress(address).build();
        PatientValidator patientValidator=new PatientValidator();
        boolean patientValidation=patientValidator.validate(patient);
        Notification<Boolean> patientNotification=new Notification<>();
        if(!patientValidation)
        {
            patientValidator.getErrors().forEach(patientNotification::addError);
            patientNotification.setResult(Boolean.FALSE);
        }
        else
        {
            patientRepository.save(patient);
            patientNotification.setResult(Boolean.TRUE);
        }
        return patientNotification;
    }

    @Override
    public Notification<Boolean> updatePatient(Long id,String name,Long idCardNr,Long cnp,String address) {
        Optional<Patient> patientOptional=patientRepository.findById(id);
        Patient patient=new Patient();
        if(patientOptional.isPresent())
        {
            patient=patientOptional.get();
        }
        patient.setName(name);
        patient.setIdCardNr(idCardNr);
        patient.setCnp(cnp);
        patient.setAddress(address);
        PatientValidator patientValidator=new PatientValidator();
        boolean patientValidation=patientValidator.validate(patient);
        Notification<Boolean> patientNotification=new Notification<>();
        if(!patientValidation)
        {
            patientValidator.getErrors().forEach(patientNotification::addError);
            patientNotification.setResult(Boolean.FALSE);
        }
        else
        {
            patientRepository.save(patient);
            patientNotification.setResult(Boolean.TRUE);
        }
        return patientNotification;
    }

    @Override
    public void deletePatient(Long id) {
        Patient patient=new PatientBuilder().setId(id).build();
        patientRepository.delete(patient);

    }

    @Override
    public List<Patient> findAllPatients() {
        return patientRepository.findAll();
    }

    @Override
    public Patient findById(Long id) {
        Optional<Patient> patientOptional = patientRepository.findById(id);
        return patientOptional.orElse(null);
    }
}
