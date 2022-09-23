package org.formflowstartertemplate.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@EnableConfigurationProperties
@ComponentScan("formflow.library")
public class AppApplication {

	public static void main(String[] args) {
		System.out.println("\uD83D\uDE80");
		System.out.println(System.getProperty("java.class.path").replace(':', '\n'));
		System.out.println("\uD83D\uDE80");

		SpringApplication.run(AppApplication.class, args);
	}

}
