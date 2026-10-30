package com.seuusuario.assistente;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AssistenteWppApplication {

	public static void main(String[] args) {
		SpringApplication.run(AssistenteWppApplication.class, args);
	}

}
