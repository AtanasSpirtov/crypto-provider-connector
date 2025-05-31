package com.example.cryptoconnector;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class TestClass implements CommandLineRunner {

    @Value("${coinbase.client-id}")
    private String clientId;

    @Value("${coinbase.client-secret}")
    private String clientSecret;

    @Override
    public void run(String... args) throws Exception {
        System.out.println(clientId);
    }
}
