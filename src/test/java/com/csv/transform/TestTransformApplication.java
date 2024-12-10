package com.csv.transform;

import org.springframework.boot.SpringApplication;

public class TestTransformApplication {

	public static void main(String[] args) {
		SpringApplication.from(TransformApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
