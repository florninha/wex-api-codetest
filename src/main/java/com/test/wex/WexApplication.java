package com.test.wex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class WexApplication {

	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(WexApplication.class, args);
	}

}
