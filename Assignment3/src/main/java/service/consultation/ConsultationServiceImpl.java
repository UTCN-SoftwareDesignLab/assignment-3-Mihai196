package service.consultation;

import model.Consultation;
import model.Patient;
import model.User;
import model.builder.ConsultationBuilder;
import model.validation.ConsultationValidator;
import model.validation.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.consultation.ConsultationRepository;
import repository.patient.PatientRepository;
import repository.user.UserRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ConsultationServiceImpl implements ConsultationService {

    @Autowired
    private ConsultationRepository consultationRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PatientRepository patientRepository;

    @Override
    public Notification<Boolean> addConsultation(Long doctorId, Long patientId, Date date) {
        Optional<User> userOptional=userRepository.findById(doctorId);
        Optional<Patient> patientOptional=patientRepository.findById(patientId);
        User user=new User();
        Patient patient=new Patient();
        if(userOptional.isPresent()&&patientOptional.isPresent())
        {
            user=userOptional.get();
            patient=patientOptional.get();
        }
        Consultation consultation=new ConsultationBuilder().setDoctor(user).setPatient(patient).setDate(date).build();
        ConsultationValidator consultationValidator=new ConsultationValidator();
        boolean consultationValidation=consultationValidator.validate(consultation);
        Notification<Boolean> consultationNotification=new Notification<>();
        if(!consultationValidation)
        {
            consultationValidator.getErrors().forEach(consultationNotification::addError);
            consultationNotification.setResult(Boolean.FALSE);
        }
        else
        {
            consultationRepository.save(consultation);
            consultationNotification.setResult(Boolean.TRUE);
        }
        return consultationNotification;
    }

    @Override
    public Notification<Boolean> updateConsultation(Long id, Date date, String description) {
        Optional<Consultation> consultationOptional=consultationRepository.findById(id);
        Consultation consultation=new Consultation();
        if(consultationOptional.isPresent())
        {
            consultation=consultationOptional.get();
        }
        consultation.setDescription(description);
        consultation.setDate(date);
        ConsultationValidator consultationValidator=new ConsultationValidator();
        boolean consultationValidation=consultationValidator.validate(consultation);
        Notification<Boolean> consultationNotification=new Notification<>();
        if(!consultationValidation)
        {
            consultationValidator.getErrors().forEach(consultationNotification::addError);
            consultationNotification.setResult(Boolean.FALSE);
        }
        else
        {
            consultationRepository.save(consultation);
            consultationNotification.setResult(Boolean.TRUE);
        }
        return consultationNotification;

    }

    @Override
    public List<Consultation> viewAllConsultations() {
        return consultationRepository.findAll();
    }

    @Override
    public void deleteConsultation(Long id) {
        Consultation consultation=new ConsultationBuilder().setId(id).build();
        consultationRepository.delete(consultation);
    }

    @Override
    public List<Consultation> findByPatient(String name) {
        List<Patient> patientsByName=patientRepository.findByName(name);
        Patient patient=patientsByName.get(0);
        return consultationRepository.findByPatient(patient);
    }

    @Override
    public Notification<Boolean> updateDescription(Long id, String description) {
        Optional<Consultation> consultationOptional=consultationRepository.findById(id);
        Consultation consultation=new Consultation();
        if(consultationOptional.isPresent())
        {
            consultation=consultationOptional.get();
        }
        String initialDescription=consultation.getDescription();
        consultation.setDescription(initialDescription+ "\n" +
                " Doctor details : " +description);
        ConsultationValidator consultationValidator=new ConsultationValidator();
        boolean consultationValidation=consultationValidator.validate(consultation);
        Notification<Boolean> consultationNotification=new Notification<>();
        if(!consultationValidation)
        {
            consultationValidator.getErrors().forEach(consultationNotification::addError);
            consultationNotification.setResult(Boolean.FALSE);
        }
        else
        {
            consultationRepository.save(consultation);
            consultationNotification.setResult(Boolean.TRUE);
        }
        return consultationNotification;
    }
}
