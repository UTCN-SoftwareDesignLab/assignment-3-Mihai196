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
    public Greeting greeting(String text) throws Exception {
        //Thread.sleep(1000); // simulated delay
        return new Greeting("Consultation details"+ text);
    }

}
