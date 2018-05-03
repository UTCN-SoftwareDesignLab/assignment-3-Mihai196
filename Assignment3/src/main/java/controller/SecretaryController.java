package controller;

import dto.PatientDTO;
import model.Patient;
import model.validation.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import service.patient.PatientService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Controller
public class SecretaryController {


    @Autowired
    private PatientService patientService;


    @RequestMapping(value = "/patient", method = RequestMethod.GET)
    public String showUserOps(Model model) {
        model.addAttribute("patientDTO",new PatientDTO());
        return "patient";
    }

    @RequestMapping(value = "/patient", params = "viewPatients", method = RequestMethod.POST)
    public String viewPatients(Model model) {
        model.addAttribute("patientDTO",new PatientDTO());
        final List<Patient> patients = patientService.findAllPatients();
        System.out.println(patients.get(0));
        model.addAttribute("patients", patients);
        return "/patient";
    }

    @RequestMapping(value = "/patient", params = "add", method = RequestMethod.POST)
    public String addPatient(Model model, @ModelAttribute("patientDTO") PatientDTO patientDTO) {
        try {
            String pattern = "dd.MM.yyyy";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            Date date = new Date(simpleDateFormat.parse(patientDTO.getDateOfBirth()).getTime());
            Notification<Boolean> patientNotification = patientService.addPatient(
                    patientDTO.getName(),patientDTO.getIdCardNr(),patientDTO.getCnp(),patientDTO.getAddress(),date);
            if (patientNotification.hasErrors()) {
                model.addAttribute("addOk", patientNotification.getFormattedErrors());
                return "/patient";
            } else {
                model.addAttribute("addOk", "New patient was added successfully");
                return "/patient";
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return "/patient";
    }
    @RequestMapping(value = "/patient",params = "update",method = RequestMethod.POST)
    public String updatePatient(Model model, @ModelAttribute("patientDTO") PatientDTO patientDTO)
    {
        Notification<Boolean> patientNotification=patientService.
                updatePatient(patientDTO.getId(),patientDTO.getName(),patientDTO.getIdCardNr(),patientDTO.getCnp(),patientDTO.getAddress());
        if (patientNotification.hasErrors()) {
            model.addAttribute("addOk", patientNotification.getFormattedErrors());
            return "/patient";
        } else {
            model.addAttribute("addOk", "Patient was updated successfully");
            return "/patient";
        }

    }
}
