package shop.geeksasang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@EnableJpaAuditing
@EnableMongoAuditing
@EnableScheduling
@SpringBootApplication
@EnableJpaRepositories(basePackages = "shop.geeksasang.repository")
@EnableMongoRepositories(basePackages = "shop.geeksasang.mongoRepository")
public class GeeksasangApplication {
	public static void main(String[] args) {
		SpringApplication.run(GeeksasangApplication.class, args);
	}
}
