package com.example.cryptoconnector;

import org.springframework.boot.SpringApplication;

public class TestCryptoConnectorApplication {

	public static void main(String[] args) {
		SpringApplication.from(CryptoConnectorApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
