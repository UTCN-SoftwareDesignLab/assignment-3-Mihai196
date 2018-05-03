package dto;

import java.util.Date;

public class PatientDTO {
    private Long id;

    private String name;

    private Long idCardNr;
    private Long cnp;

    private String dateOfBirth;
    private String address;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getIdCardNr() {
        return idCardNr;
    }

    public Long getCnp() {
        return cnp;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIdCardNr(Long idCardNr) {
        this.idCardNr = idCardNr;
    }

    public void setCnp(Long cnp) {
        this.cnp = cnp;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
