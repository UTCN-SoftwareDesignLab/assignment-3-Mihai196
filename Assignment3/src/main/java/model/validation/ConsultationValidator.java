package model.validation;

import model.Consultation;

import java.util.ArrayList;
import java.util.List;

public class ConsultationValidator {

    private final List<String> errors;

    public List<String> getErrors() {
        return errors;
    }
    public ConsultationValidator()
    {
        errors=new ArrayList<>();
    }
    public boolean validate(Consultation consultation)
    {
        if(!consultation.getDoctor().getRole().equals("doctor"))
        {
            errors.add("Consultation can only be performed by a doctor");
        }
        if(consultation.getPatient()==null)
        {
            errors.add("Patient for consultation must exist");
        }
        return errors.isEmpty();

    }
}
