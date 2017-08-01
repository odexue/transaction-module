package com.n26.transactionsmodule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.n26.transactionsmodule",
		"com.n26.transactionsmodule.api",
		"com.n26.transactionsmodule.services",
		"com.n26.transactionsmodule.db"})
public class TransactionsModuleApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransactionsModuleApplication.class, args);
	}
}
