package com.productStore.client_service;

import org.springframework.boot.SpringApplication;

public class TestSaleServiceApplication {

	public static void main(String[] args) {
		SpringApplication.from(ClientServiceApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
