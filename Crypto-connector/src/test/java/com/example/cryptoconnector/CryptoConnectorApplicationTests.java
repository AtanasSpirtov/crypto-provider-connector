package com.example.cryptoconnector;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class CryptoConnectorApplicationTests {

	@Test
	void contextLoads() {
	}

}
