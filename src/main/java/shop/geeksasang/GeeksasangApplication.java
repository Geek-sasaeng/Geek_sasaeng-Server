package shop.geeksasang;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import shop.geeksasang.service.SendEmailService;

@EnableJpaAuditing
@SpringBootApplication
public class GeeksasangApplication {
	public static void main(String[] args) {
		SpringApplication.run(GeeksasangApplication.class, args);
	}
}
