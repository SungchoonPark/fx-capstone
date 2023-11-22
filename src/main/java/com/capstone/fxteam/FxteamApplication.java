package com.capstone.fxteam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class FxteamApplication {

	public static void main(String[] args) {
		SpringApplication.run(FxteamApplication.class, args);
	}

}
