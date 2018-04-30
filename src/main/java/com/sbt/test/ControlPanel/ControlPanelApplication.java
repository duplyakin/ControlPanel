package com.sbt.test.ControlPanel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.sbt.test")
public class ControlPanelApplication {

	public static void main(String[] args) {
		SpringApplication.run(ControlPanelApplication.class, args);
	}
}
