package service.consultation;

import model.Consultation;
import model.validation.Notification;

import java.util.Date;
import java.util.List;

public interface ConsultationService {
    Notification<Boolean> addConsultation(Long doctorId,Long patientId,Date date);
    Notification<Boolean> updateConsultation(Long id,Date date,String description);
    List<Consultation> viewAllConsultations();
    void deleteConsultation(Long id);
    List<Consultation> findByPatient(String name);
    Notification<Boolean> updateDescription(Long id,String description);
    int findAvailabiltyDoctor(Long doctorId,Date date);

}
