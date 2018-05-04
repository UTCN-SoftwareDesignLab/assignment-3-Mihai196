package repository.consultation;

import model.Consultation;
import model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConsultationRepository extends JpaRepository<Consultation,Long> {

    List<Consultation> findByPatient (Patient patient);
}
