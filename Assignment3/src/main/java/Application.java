import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("model")
@EnableJpaRepositories({"repository.user"})
@ComponentScan({"model","repository.user","service.user","controller","config"})
public class Application {

    public static void main(String []args)
    {
        SpringApplication.run(Application.class, args);
    }
}
