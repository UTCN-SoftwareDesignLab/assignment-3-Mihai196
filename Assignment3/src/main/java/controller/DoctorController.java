package controller;

import model.Consultation;
import model.validation.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import service.consultation.ConsultationService;
import service.patient.PatientService;

import java.util.List;

@Controller
public class DoctorController {

    @Autowired
    private PatientService patientService;
    @Autowired
    private ConsultationService consultationService;

    @RequestMapping(value = "/doctor",method = RequestMethod.GET)
    public String showDoctor(Model model)
    {
        return "/doctor";
    }
    @RequestMapping(value = "/doctor",method = RequestMethod.POST,params = "viewConsultations")
    public String findConsultations(Model model, @RequestParam("name") String name)
    {
        List<Consultation> consultations=consultationService.findByPatient(name);
        model.addAttribute("consultations",consultations);
        return "/doctor";
    }
    @RequestMapping(value = "/doctor",method = RequestMethod.POST,params = "addDetails")
    public String addDetailsToConsultation(Model model,@RequestParam("id") Long id,@RequestParam("description") String description)
    {
        Notification<Boolean> consultationNotifcation = consultationService.updateDescription(id,description);
        if (consultationNotifcation.hasErrors()) {
            model.addAttribute("addOk", consultationNotifcation.getFormattedErrors());
            return "/doctor";
        } else {
            model.addAttribute("addOk", "Consultation was updated successfully");
            return "/doctor";
        }
    }
}
