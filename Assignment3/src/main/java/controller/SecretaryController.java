package controller;

import dto.ConsultationDTO;
import dto.PatientDTO;
import model.Consultation;
import model.Patient;
import model.User;
import model.validation.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import service.consultation.ConsultationService;
import service.patient.PatientService;
import service.user.UserService;
import socket.Greeting;
import socket.HelloMessage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Controller
public class SecretaryController {


    @Autowired
    private PatientService patientService;
    @Autowired
    private ConsultationService consultationService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/patient", method = RequestMethod.GET)
    public String showUserOps(Model model) {
        model.addAttribute("patientDTO", new PatientDTO());
        return "patient";
    }

    @RequestMapping(value = "/patient", params = "viewPatients", method = RequestMethod.POST)
    public String viewPatients(Model model) {
        model.addAttribute("patientDTO", new PatientDTO());
        final List<Patient> patients = patientService.findAllPatients();
        System.out.println(patients.get(0));
        model.addAttribute("patients", patients);
        return "/patient";
    }

    @RequestMapping(value = "/patient", params = "viewDoctors", method = RequestMethod.POST)
    public String viewDoctors(Model model) {
        model.addAttribute("patientDTO", new PatientDTO());
        final List<User> users = userService.findAll();
        List<User> doctors = users.stream().filter(d -> d.getRole().equals("doctor")).collect(Collectors.toList());
        model.addAttribute("users", doctors);
        return "/patient";
    }

    @RequestMapping(value = "/patient", params = "add", method = RequestMethod.POST)
    public String addPatient(Model model, @ModelAttribute("patientDTO") PatientDTO patientDTO) {
        try {
            String pattern = "dd.MM.yyyy";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            Date date = new Date(simpleDateFormat.parse(patientDTO.getDateOfBirth()).getTime());
            Notification<Boolean> patientNotification = patientService.addPatient(
                    patientDTO.getName(), patientDTO.getIdCardNr(), patientDTO.getCnp(), patientDTO.getAddress(), date);
            if (patientNotification.hasErrors()) {
                model.addAttribute("addOk", patientNotification.getFormattedErrors());
                return "/patient";
            } else {
                model.addAttribute("addOk", "New patient was added successfully");
                return "/patient";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/patient";
    }

    @RequestMapping(value = "/patient", params = "update", method = RequestMethod.POST)
    public String updatePatient(Model model, @ModelAttribute("patientDTO") PatientDTO patientDTO) {
        Notification<Boolean> patientNotification = patientService.
                updatePatient(patientDTO.getId(), patientDTO.getName(), patientDTO.getIdCardNr(), patientDTO.getCnp(), patientDTO.getAddress());
        if (patientNotification.hasErrors()) {
            model.addAttribute("addOk", patientNotification.getFormattedErrors());
            return "/patient";
        } else {
            model.addAttribute("addOk", "Patient was updated successfully");
            return "/patient";
        }
    }

    @RequestMapping(value = "/patient", params = "scheduleConsultations", method = RequestMethod.POST)
    public String redConsultations(Model model) {
        return "redirect:/consultation";
    }

    @RequestMapping(value = "/consultation", method = RequestMethod.GET)
    public String showConsultations(Model model) {
        model.addAttribute("consultationDTO", new ConsultationDTO());
        return "/consultation";
    }

    @RequestMapping(value = "/addConsultation", method = RequestMethod.GET)
    public String addConsultation(Model model, @ModelAttribute("consultationDTO") ConsultationDTO consultationDTO,
    @RequestParam String datec , @RequestParam long patientId, @RequestParam long doctorId) {
        model.addAttribute("patientDTO", new PatientDTO());
        try {
            String pattern = "dd.MM.yyyy";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            Date dateParsed = new Date(simpleDateFormat.parse(datec).getTime());
            if(consultationService.findAvailabiltyDoctor(doctorId,dateParsed)<5) {
                Notification<Boolean> consultationNotifcation = consultationService
                        .addConsultation(consultationDTO.getDoctorId(), consultationDTO.getPatientId(), dateParsed);
                if (consultationNotifcation.hasErrors()) {
                    model.addAttribute("addOk", consultationNotifcation.getFormattedErrors());
                    return "/consultation";
                } else {
                    model.addAttribute("addOk", "New consultation was added successfully");
                    return "/consultation";
                }
            }
            else
            {
                model.addAttribute("addOk","Doctor already has more than 5 consultations on that day");
                return  "/consultation";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/consultation";
    }

    @RequestMapping(value = "/consultation", params = "viewConsultations", method = RequestMethod.POST)
    public String viewAllConsultations(Model model) {
        model.addAttribute("patientDTO", new PatientDTO());
        model.addAttribute("consultationDTO", new ConsultationDTO());
        final List<Consultation> consultations = consultationService.viewAllConsultations();
        model.addAttribute("consultations", consultations);
        return "/consultation";
    }

    @RequestMapping(value = "backToPatient", method = RequestMethod.GET)
    public String backToPatient(Model model) {
        return "redirect:/patient";
    }

    @RequestMapping(value = "/consultation", params = "updateC", method = RequestMethod.POST)
    public String updatePatient(Model model, @ModelAttribute("consultationDTO") ConsultationDTO consultationDTO) {
        try {
            String pattern = "dd.MM.yyyy";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            Date date = new Date(simpleDateFormat.parse(consultationDTO.getDate()).getTime());
            Notification<Boolean> consultationNotification =
                    consultationService.updateConsultation(consultationDTO.getId(), date, consultationDTO.getDescription());
            if (consultationNotification.hasErrors()) {
                model.addAttribute("addOk", consultationNotification.getFormattedErrors());
                return "/consultation";
            } else {
                model.addAttribute("addOk", "Consultation was updated successfully");
                return "/consultation";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/consultation";
    }

    @RequestMapping(value = "/consultation", params = "deleteC", method = RequestMethod.POST)
    public String deleteConsultation(Model model, @ModelAttribute("consultationDTO") ConsultationDTO consultationDTO) {
        consultationService.deleteConsultation(consultationDTO.getId());
        model.addAttribute("addOk", "Consultation was deleted successfully");
        return "/consultation";
    }
}
