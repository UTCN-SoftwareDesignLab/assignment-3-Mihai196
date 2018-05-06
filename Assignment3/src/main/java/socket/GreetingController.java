package socket;

import model.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import service.consultation.ConsultationService;
import service.patient.PatientService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class GreetingController {

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {
        //Thread.sleep(1000); // simulated delay
        return new Greeting("Hello, " + message.getName() + "!");
    }

   /* @Autowired
    private ConsultationService consultationService;
    @Autowired
    private PatientService patientService;*/

   /*@MessageMapping("/consult")
   @SendTo("/topic/consult")

    public Greeting greeting(ConsultationMessage consultationMessage) throws ParseException {
        Patient patient = patientService.findById(consultationMessage.getPatientId());
        String pattern = "dd.MM.yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Date date = new Date(simpleDateFormat.parse(consultationMessage.getDate()).getTime());
        String greetingText="You have appointment with patient" + patient.getName() + " at date " +date +"with details";
        return new Greeting(greetingText);
    }*/



}
