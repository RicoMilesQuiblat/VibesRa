package com.NeuralN.VibesRa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.NeuralN.VibesRa.*","org.springdoc"})
public class VibesRaApplication {

	public static void main(String[] args) {
		SpringApplication.run(VibesRaApplication.class, args);
	}

}
