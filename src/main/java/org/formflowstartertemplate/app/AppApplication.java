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

		SpringApplication.run(AppApplication.class, args);
	}

}
