package com.example.cryptoconnector;

import com.example.cryptoconnector.sdk.oauth2.model.properties.BinanceApplicationProperties;
import com.example.cryptoconnector.sdk.oauth2.model.properties.CoinbaseApplicationProperties;
import com.example.cryptoconnector.sdk.trade.binance.kafka.KafkaProperties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableConfigurationProperties({
    CoinbaseApplicationProperties.class,
    BinanceApplicationProperties.class,
    KafkaProperties.class
})
@EnableFeignClients
public class CryptoConnectorApplication {

	public static void main(String[] args) {
		SpringApplication.run(CryptoConnectorApplication.class, args);
	}

}
