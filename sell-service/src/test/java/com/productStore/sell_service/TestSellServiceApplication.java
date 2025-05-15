package com.productStore.sell_service;

import org.springframework.boot.SpringApplication;

public class TestSellServiceApplication {

	public static void main(String[] args) {
		SpringApplication.from(SellServiceApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
