package repository.consultation;

import model.Consultation;
import model.Patient;
import model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface ConsultationRepository extends JpaRepository<Consultation,Long> {

    List<Consultation> findByPatient (Patient patient);
    List<Consultation> findByDoctorAndDate(User doctor, Date date);
}
