package com.productStore.sale_service;

import org.springframework.boot.SpringApplication;

public class TestSaleServiceApplication {

	public static void main(String[] args) {
		SpringApplication.from(SaleServiceApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
