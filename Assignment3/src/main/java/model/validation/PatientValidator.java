package model.validation;

import dto.PatientDTO;
import model.Patient;

import java.util.ArrayList;
import java.util.List;

public class PatientValidator {


    private final List<String> errors;

    private static final int NR_DIGITS_PNC=10;
    private static final int NR_DIGITS_IDCARD=4;

    public List<String> getErrors() {
        return errors;
    }

    public PatientValidator() {
        errors = new ArrayList<>();
    }

    public boolean validate(Patient patient)
    {

        if (String.valueOf(patient.getIdCardNr()).length()!=NR_DIGITS_IDCARD)
        {
            errors.add("Patient ID Card number must contain exactly 4 digits");
        }
        if (String.valueOf(patient.getCnp()).length()!=NR_DIGITS_PNC)
        {
            errors.add("Patient PNC must contain exactly 10 digits");
        }
        return errors.isEmpty();
    }


}
