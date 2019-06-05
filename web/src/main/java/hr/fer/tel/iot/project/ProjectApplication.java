package hr.fer.tel.iot.project;

import hr.fer.tel.iot.project.cloud.ReadDeviceToCloudMessages;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication

public class ProjectApplication {

	public static void main(String[] args) {

		SpringApplication.run(ProjectApplication.class, args);
		//new Thread(new ReadDeviceToCloudMessages()).start();
	}

}
