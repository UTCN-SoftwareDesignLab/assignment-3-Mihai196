package model.builder;

import model.Patient;

import java.util.Date;

public class PatientBuilder {

    private Patient patient;

    public PatientBuilder()
    {
        patient=new Patient();
    }

    public PatientBuilder setId(Long id)
    {
        patient.setId(id);
        return this;
    }
    public PatientBuilder setName(String name)
    {
        patient.setName(name);
        return this;
    }
    public PatientBuilder setIdCardNr(Long idCardNr)
    {
        patient.setIdCardNr(idCardNr);
        return this;
    }
    public PatientBuilder setCNP(Long cnp)
    {
        patient.setCnp(cnp);
        return this;
    }
    public PatientBuilder setDateOfBirth(Date dateOfBirth)
    {
        patient.setDateOfBirth(dateOfBirth);
        return this;
    }
    public PatientBuilder setAddress(String address)
    {
        patient.setAddress(address);
        return this;
    }
    public Patient build()
    {
        return patient;
    }
}
