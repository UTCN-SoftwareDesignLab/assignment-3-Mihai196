package model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @Column(unique = true)
    private Long idCardNr;

    @Column(unique = true)
    private Long cnp;

    private Date dateOfBirth;
    private String address;

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

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setAddress(String address) {
        this.address = address;
    }

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

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public String getAddress() {
        return address;
    }
}
