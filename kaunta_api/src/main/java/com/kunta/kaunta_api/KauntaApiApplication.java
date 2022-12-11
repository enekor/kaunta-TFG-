package com.kunta.kaunta_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
public class KauntaApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(KauntaApiApplication.class, args);
	}

}
