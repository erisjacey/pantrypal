package com.pantrypal.grocerytracker;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class PantrypalBackendApplicationTests {

	@Test
	@DisplayName("Test context loads")
	void contextLoads() {
	}

}
