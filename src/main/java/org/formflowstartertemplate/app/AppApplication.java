package org.formflowstartertemplate.app;

import formflow.library.config.FlowsConfigurationFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class AppApplication {

	public static void main(String[] args) {
		FlowsConfigurationFactory flowsConfigurationFactory = new FlowsConfigurationFactory();
		SpringApplication.run(AppApplication.class, args);
	}

}
