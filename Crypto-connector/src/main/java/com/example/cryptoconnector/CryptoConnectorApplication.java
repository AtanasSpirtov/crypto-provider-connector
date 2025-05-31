package com.example.cryptoconnector;

import com.example.cryptoconnector.sdk.auth.model.properties.CoinbaseApplicationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableConfigurationProperties(CoinbaseApplicationProperties.class)
@EnableFeignClients
public class CryptoConnectorApplication {

	public static void main(String[] args) {
		SpringApplication.run(CryptoConnectorApplication.class, args);
	}

}
