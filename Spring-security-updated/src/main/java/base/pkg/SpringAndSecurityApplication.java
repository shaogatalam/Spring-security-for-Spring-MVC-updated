package base.pkg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringAndSecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringAndSecurityApplication.class, args);
		System.out.println("Spring boot application started successfully...");
	}

}
