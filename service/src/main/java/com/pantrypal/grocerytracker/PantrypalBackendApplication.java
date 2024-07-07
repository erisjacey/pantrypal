package com.pantrypal.grocerytracker;

import com.pantrypal.grocerytracker.config.properties.RsaKeyConfigProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(RsaKeyConfigProperties.class)
public class PantrypalBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(PantrypalBackendApplication.class, args);
	}

}
