package socket;

public class ConsultationMessage {

    private Long doctorId;
    private Long patientId;
    private String date;

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public Long getPatientId() {
        return patientId;
    }

    public String getDate() {
        return date;
    }
}
