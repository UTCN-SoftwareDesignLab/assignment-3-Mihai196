package controller;

import dto.ConsultationDTO;
import dto.PatientDTO;
import model.Consultation;
import model.Patient;
import model.validation.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import service.consultation.ConsultationService;
import service.patient.PatientService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Controller
public class SecretaryController {


    @Autowired
    private PatientService patientService;
    @Autowired
    private ConsultationService consultationService;


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
    @RequestMapping(value = "/patient",params = "scheduleConsultations",method = RequestMethod.POST)
    public String redConsultations(Model model)
    {
        return "redirect:/consultation";
    }
    @RequestMapping(value="/consultation",method = RequestMethod.GET)
    public String showConsultations(Model model)
    {
        model.addAttribute("consultationDTO",new ConsultationDTO());
        return "/consultation";
    }
    @RequestMapping(value = "/consultation",params = "addC",method = RequestMethod.POST)
    public String addConsultation(Model model,@ModelAttribute("consultationDTO") ConsultationDTO consultationDTO)
    {
        model.addAttribute("patientDTO",new PatientDTO());
        try {
            String pattern = "dd.MM.yyyy";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            Date date = new Date(simpleDateFormat.parse(consultationDTO.getDate()).getTime());
            Notification<Boolean> consultationNotifcation = consultationService
                    .addConsultation(consultationDTO.getDoctorId(), consultationDTO.getPatientId(),date, consultationDTO.getDescription());
            if (consultationNotifcation.hasErrors()) {
                model.addAttribute("addOk", consultationNotifcation.getFormattedErrors());
                return "/consultation";
            } else {
                model.addAttribute("addOk", "New consultation was added successfully");
                return "/consultation";
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return "/consultation";
    }
    @RequestMapping(value = "/consultation",params = "viewConsultations",method = RequestMethod.POST)
    public String viewAllConsultations(Model model)
    {
        model.addAttribute("patientDTO",new PatientDTO());
        model.addAttribute("consultationDTO",new ConsultationDTO());
        final List<Consultation> consultations = consultationService.viewAllConsultations();
        model.addAttribute("consultations", consultations);
        return "/consultation";
    }
    @RequestMapping(value ="backToPatient",method = RequestMethod.GET)
    public String backToPatient(Model model)
    {
        return "redirect:/patient";
    }
    @RequestMapping(value = "/consultation",params = "updateC",method = RequestMethod.POST)
    public String updatePatient(Model model,@ModelAttribute("consultationDTO") ConsultationDTO consultationDTO)
    {
        try {
            String pattern = "dd.MM.yyyy";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            Date date = new Date(simpleDateFormat.parse(consultationDTO.getDate()).getTime());
            Notification<Boolean> consultationNotifcation =
                    consultationService.updateConsultation(consultationDTO.getId(),date,consultationDTO.getDescription());
            if (consultationNotifcation.hasErrors()) {
                model.addAttribute("addOk", consultationNotifcation.getFormattedErrors());
                return "/consultation";
            } else {
                model.addAttribute("addOk", "Consultation was updated successfully");
                return "/consultation";
            }

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return "/consultation";
    }
    @RequestMapping(value = "/consultation",params = "deleteC",method = RequestMethod.POST)
    public String deleteConsultation(Model model,@ModelAttribute("consultationDTO") ConsultationDTO consultationDTO)
    {
        consultationService.deleteConsultation(consultationDTO.getId());
        model.addAttribute("addOk","Consultation was deleted successfully");
        return "/consultation";
    }
}
