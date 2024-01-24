package io.github.rajendrasatpute.samplespringbootapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SampleSpringbootApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SampleSpringbootApiApplication.class, args);
	}

}
